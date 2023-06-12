package com.example.assignment2

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_fdactivity.*
import java.util.*

class FDActivity : BaseActivity() {

    private lateinit var currentUser: User
    private var durationText: String = ""

    var duration: Int = 0
    var rate: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fdactivity)

        val accountNo = intent.getStringExtra(Constants.USERACC)
        val json = PrefUtils.with(this).getUser(accountNo!!)
        currentUser = Gson().fromJson(json, User::class.java)

        setupActionBar()
        setupSpinner()

        img_more_info.setOnClickListener {
            moreInfoDialog()
        }

        btn_fd_create.setOnClickListener {
            it.hideKeyboard()

            if(et_fd_amount.text!!.isEmpty()){
                showErrorSnackBar(getString(R.string.error_fd_amount), true)
            }
            else if(et_fd_amount.text.toString().toDouble() > currentUser.balance) {
                showErrorSnackBar(getString(R.string.error_insufficient_balance), true)
            }
            else {
                createFD()
            }
        }
    }

    private fun createFD() {
        val newFD = FD(
            currentUser.accNo,
            et_fd_amount.text.toString().toDouble(),
            duration,
            rate,
            Calendar.getInstance().time
        )

        currentUser.balance -= et_fd_amount.text.toString().toDouble()
        currentUser.fds.add(newFD)

        PrefUtils.with(this).saveUser(currentUser)
        showErrorSnackBar(getString(R.string.success_fd), false)

        Log.e("FD", newFD.toString())
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter<String>(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.fd_duration)
        )
        spinner_fd_duration.adapter = adapter

        spinner_fd_duration.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterview: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                durationText = adapterview?.getItemAtPosition(position).toString()
                duration = Constants.durationList[position]
                rate = Constants.rateList[position]

                tv_fd_rate.text = "$rate%"
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

        val btnClose = dialogBinding.findViewById<ImageView>(R.id.btn_close)
        btnClose.setOnClickListener {
            ratesDialog.dismiss()
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_fd_activity)

        val actionBar = supportActionBar
        if(actionBar != null) {
            actionBar.title = ""
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        toolbar_fd_activity.setNavigationOnClickListener{ onBackPressed() }
    }
}