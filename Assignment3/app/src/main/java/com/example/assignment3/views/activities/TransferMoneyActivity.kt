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
        balance = userViewModel.getBalance(this, accountNo)

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

        userViewModel.getUser(this, receiver).observe(this) {
            if (it.isEmpty()) {
                showErrorSnackBar(getString(R.string.error_no_user_exists), true)
            }
            else {
                if(it.first().ifscCode != binding.etTransferIfsc.text.toString()) {
                    showErrorSnackBar(getString(R.string.error_ifsc_mismatch), true)
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
                    userViewModel.saveReceiptDialog(this, transaction)
                    showErrorSnackBar(getString(R.string.success_transfer), false)
                }
            }
        }
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