package com.example.assignment3.views.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.assignment3.*
import com.example.assignment3.model.FD
import com.example.assignment3.Utils.Constants
import com.example.assignment3.Utils.DBUtils
import com.example.assignment3.Utils.DownloadWorker
import com.example.assignment3.UserViewModel
import com.example.assignment3.databinding.ActivityFdactivityBinding
import kotlinx.android.synthetic.main.rates_dialog.view.*
import java.time.LocalDate

class FDActivity : BaseActivity() {

    private lateinit var binding: ActivityFdactivityBinding
    private lateinit var fd: FD
    private lateinit var accountNo: String
    private var balance: Double? = 0.0
    lateinit var userViewModel: UserViewModel

    private var durationText: String = ""
    private var duration: Int = 0
    private var rate: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityFdactivityBinding.inflate(layoutInflater)
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

        setupSpinner()

    }

    fun validateFD(view: View) {
        view.hideKeyboard()

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

    private fun createFD() {
        val amount = binding.etFdAmount.text.toString().toDouble()
        fd = FD(
            accountNo,
            amount,
            duration,
            rate,
            LocalDate.now().toString()
        )

        userViewModel.makeFD(this, accountNo, amount)

        userViewModel.saveReceiptDialog(this, fd)
        showErrorSnackBar(getString(R.string.success_fd), false)
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

    fun moreInfoDialog(view: View) {
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