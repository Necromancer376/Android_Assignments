package com.example.assignment3.views.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.example.assignment3.model.Transactions
import com.example.assignment3.R
import com.example.assignment3.Utils.Constants
import com.example.assignment3.Utils.DBUtils
import com.example.assignment3.Utils.DownloadWorker
import com.example.assignment3.UserViewModel
import com.example.assignment3.databinding.ActivityTransferMoneyBinding
import java.time.LocalDate

class TransferMoneyActivity : BaseActivity() {

    private lateinit var binding: ActivityTransferMoneyBinding
    private lateinit var accountNo: String
    private lateinit var transaction: Transactions
    lateinit var userViewModel: UserViewModel


    private var balance: Double? = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityTransferMoneyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        setupActionBar()
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        accountNo = intent.getStringExtra(Constants.ACCOUNTNO)!!
        balance = DBUtils.with(this).getDB().userDao().getBalance(accountNo)

    }

    fun validateTransfer(view: View) {
        view.hideKeyboard()

        if (binding.etTransferName.text!!.isEmpty()) {
            showErrorSnackBar(getString(R.string.error_transfer_name), true)
        } else if (binding.etTransferAccount.text!!.length != 11) {
            showErrorSnackBar(getString(R.string.error_transfer_account), true)
        } else if (binding.etTransferAccount.text!!.toString() == accountNo) {
            showErrorSnackBar(getString(R.string.error_acc_duplicate), true)
        } else if (binding.etTransferIfsc.text!!.length != 11) {
            showErrorSnackBar(getString(R.string.error_transfer_ifsc), true)
        } else if (binding.etTransferAmount.text!!.isEmpty()) {
            showErrorSnackBar(getString(R.string.error_transfer_amount), true)
        } else if (binding.etTransferAmount.text.toString().toDouble() > balance!!) {
            showErrorSnackBar(getString(R.string.error_insufficient_balance), true)
        } else {
            transferMoney()
        }
    }

    private fun transferMoney() {

        val receiver = binding.etTransferAccount.text.toString()
        val amount = binding.etTransferAmount.text.toString().toDouble()

        DBUtils.with(this).getDB().userDao().getUser(receiver).observe(this) {
            if (it.isEmpty()) {
                showErrorSnackBar(getString(R.string.error_no_user_exists), true)
            }
            else {
                if(it.first().ifscCode != binding.etTransferIfsc.text.toString()) {
                    showErrorSnackBar("IFSC No. does not match", true)
                }
                else {
                    userViewModel.transferMoney(this, accountNo, receiver, amount)

                    transaction = Transactions(
                        accountNo,
                        receiver,
                        amount,
                        LocalDate.now().toString()
                    )

                    Log.e("transaction", transaction.toString())
                    saveReceiptDialog()
                    showErrorSnackBar(getString(R.string.success_transfer), false)
                }
            }
        }
    }

    fun saveReceiptDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Download Receipt of the Transaction")
        builder.setTitle("Receipt Download")
        builder.setCancelable(false)

        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener { dialogInterface, i ->
            Log.e("Dialog", "Yes")

            val downloadWorkRequest = OneTimeWorkRequest.Builder(DownloadWorker::class.java)
            val data = Data.Builder()
            val filename = "transaction_" + transaction.id.toString() + ".txt"
            data.putString("FILE_CONTENT", transaction.toString())
            data.putString("FILE_NAME", filename)
            downloadWorkRequest.setInputData(data.build())

            WorkManager.getInstance(this).enqueue(downloadWorkRequest.build())

            dialogInterface.cancel()
        }))

        builder.setNegativeButton("No", (DialogInterface.OnClickListener { dialogInterface, i ->
            Log.e("Dialog", "Yes")
            dialogInterface.cancel()
        }))

        val downloadDialog = builder.create()
        downloadDialog.show()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarTransferActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = ""
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        binding.toolbarTransferActivity.setNavigationOnClickListener { onBackPressed() }
    }
}