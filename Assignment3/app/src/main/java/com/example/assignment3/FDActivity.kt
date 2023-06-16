package com.example.assignment3

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.assignment3.databinding.ActivityFdactivityBinding
import kotlinx.android.synthetic.main.rates_dialog.view.*
import java.time.LocalDate

class FDActivity : BaseActivity() {

    private lateinit var binding: ActivityFdactivityBinding
    private lateinit var fd: FD
    private lateinit var accountNo: String
    private var balance: Double? = 0.0

    private var durationText: String = ""
    private var duration: Int = 0
    private var rate: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityFdactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        accountNo = intent.getStringExtra(Constants.ACCOUNTNO)!!
        balance = DBUtils.with(this).getDB().userDao().getBalance(accountNo)

        setupSpinner()

        binding.imgMoreInfo.setOnClickListener {
            moreInfoDialog()
        }

        binding.btnFdCreate.setOnClickListener {
            it.hideKeyboard()

            if(binding.etFdAmount.text!!.isEmpty()){
                showErrorSnackBar(getString(R.string.error_fd_amount), true)
            }
            else if(binding.etFdAmount.text.toString().toDouble() > balance!!) {
                showErrorSnackBar(getString(R.string.error_insufficient_balance), true)
            }
            else if (duration == 0){
                showErrorSnackBar(getString(R.string.error_fill_duration), true)
            }
            else {
                createFD()
            }
        }
    }

    private fun createFD() {
        val amount = binding.etFdAmount.text.toString().toDouble()
        fd = FD(
            accountNo,
            amount,
            duration,
            rate,
            LocalDate.now().toString()
        )

        DBUtils.with(this).getDB().userDao().reduceMoney(accountNo, amount)
        saveReceiptDialog()
        showErrorSnackBar(getString(R.string.success_fd), false)
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
            val filename = "fd_" + fd.id.toString() + ".txt"
            data.putString("FILE_CONTENT", fd.toString())
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

    private fun setupSpinner() {
        val adapter = ArrayAdapter<String>(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.fd_duration)
        )
        binding.spinnerFdDuration.adapter = adapter

        binding.spinnerFdDuration.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterview: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                durationText = adapterview?.getItemAtPosition(position).toString()
                duration = Constants.durationList[position]
                rate = Constants.rateList[position]

                binding.rate = "$rate%"
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun moreInfoDialog() {
        val dialogBinding = layoutInflater.inflate(R.layout.rates_dialog, null)
        val ratesDialog = Dialog(this)
        ratesDialog.setContentView(dialogBinding)
        ratesDialog.setCancelable(true)
        ratesDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        ratesDialog.show()

        val btnClose = dialogBinding.btn_close
        btnClose.setOnClickListener {
            ratesDialog.dismiss()
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarFdActivity)

        val actionBar = supportActionBar
        if(actionBar != null) {
            actionBar.title = ""
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        binding.toolbarFdActivity.setNavigationOnClickListener{ onBackPressed() }
    }
}