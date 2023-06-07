package com.example.assignment2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private lateinit var accoutNo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_create_user.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            accoutNo = et_login_acc.text.toString()

            val sharedPreferences = getSharedPreferences("UserPreference", MODE_PRIVATE)
            val json = sharedPreferences.getString(accoutNo, null)

            Log.e("json", json.toString())

            it.hideKeyboard()

            if(json != null) {
                val user = Gson().fromJson(json, User::class.java)
                Log.e("user", user.toString())

                if (et_login_pincode?.text.toString() == user.pincode) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("ACCNO", accoutNo)
                    startActivity(intent)
                } else {
                    showErrorSnackBar(getString(R.string.error_invalid_credentials), true)
                }
            }
            else {
                showErrorSnackBar(getString(R.string.error_no_user_exists), true)
            }
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}