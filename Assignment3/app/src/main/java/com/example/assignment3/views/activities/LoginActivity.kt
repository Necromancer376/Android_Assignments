package com.example.assignment3.views.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import com.example.assignment3.Utils.Constants
import com.example.assignment3.Utils.DBUtils
import com.example.assignment3.R
import com.example.assignment3.model.User
import com.example.assignment3.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityLoginBinding.inflate(layoutInflater)
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
    }

    fun login(view: View) {
        val accoutNo = binding.etLoginAcc.text.toString()
        val pincode = binding.etLoginPincode.text.toString()

        Log.e("Login", accoutNo + ", " + pincode)
        view.hideKeyboard()

        if (binding.etLoginAcc.text!!.length != 11) {
            showErrorSnackBar(getString(R.string.error_account), true)
        }
        else {
            try {
                var loginCredentials: User? = null
                loginCredentials = DBUtils.with(this).getDB().userDao()
                    .loginValidation(accoutNo, pincode)
//                    .observe(this) {
//                        loginCredentials = it
//                        Log.e("Login it", it.toString())
//                    }

                Log.e("Login variable", loginCredentials.toString())

                if (loginCredentials == null) {
                    showErrorSnackBar(getString(R.string.error_invalid_credentials), true)
                } else {
                    showErrorSnackBar(getString(R.string.login_success), false)
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra(Constants.ACCOUNTNO, accoutNo)
                    startActivity(intent)
                }
            } catch (e: Exception) {
                Log.e("Login", "Failure")
            }
        }
    }

    fun toRegister(view: View) {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }
}