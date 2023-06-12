package com.example.assignment2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.text.toSpanned
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.btn_transfer
import kotlinx.android.synthetic.main.activity_transfer_money.*

class TransferMoneyActivity : BaseActivity() {

    private lateinit var currentUser: User
    private lateinit var transferUser: User
    private var amount: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_money)

        setupActionBar()

        val currentAcc = intent.getStringExtra(Constants.USERACC)
        val json = PrefUtils.with(this).getUser(currentAcc!!)
        currentUser = Gson().fromJson(json, User::class.java)

        btn_transfer.setOnClickListener {
            it.hideKeyboard()
            validateTransfer()
        }
    }

    private fun validateTransfer() {
        if(et_transfer_amount.text.toString().toDouble() > currentUser.balance) {
            showErrorSnackBar(getString(R.string.error_insufficient_balance), true)
        }

        else if(et_transfer_name.text!!.isEmpty()) {
            showErrorSnackBar(getString(R.string.error_transfer_name), true)
        }

        else if(et_transfer_account.text!!.length != 11) {
            showErrorSnackBar(getString(R.string.error_transfer_account), true)
        }

        else if(et_transfer_ifsc.text!!.length != 11) {
            showErrorSnackBar(getString(R.string.error_transfer_ifsc), true)
        }

        else if(et_transfer_amount.text!!.isEmpty()) {
            showErrorSnackBar(getString(R.string.error_transfer_amount), true)
        }

        else {
            transferMoney()
        }
    }

    private fun transferMoney() {
        val transferJson = PrefUtils.with(this).getUser(et_transfer_account.text.toString())

        if(transferJson == null){
            showErrorSnackBar(getString(R.string.error_no_user_exists), true)
        }
        else {
            transferUser = Gson().fromJson(transferJson, User::class.java)
            amount = et_transfer_amount.text.toString().toDouble()
            currentUser.balance -= amount
            transferUser.balance += amount

            storeTransaction()

            showErrorSnackBar(getString(R.string.success_transfer), false)
        }
    }

    private fun storeTransaction() {
        PrefUtils.with(this).transferMoney(currentUser, transferUser)
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_transfer_activity)

        val actionBar = supportActionBar
        if(actionBar != null) {
            actionBar.title = ""
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        toolbar_transfer_activity.setNavigationOnClickListener{ onBackPressed() }
    }
}