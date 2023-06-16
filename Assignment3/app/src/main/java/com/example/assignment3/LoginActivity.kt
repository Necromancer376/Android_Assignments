package com.example.assignment3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.example.assignment3.databinding.ActivityLoginBinding
import kotlin.math.log2

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvCreateUser.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val accoutNo = binding.etLoginAcc.text.toString()
            val pincode = binding.etLoginPincode.text.toString()

            Log.e("Login", accoutNo + ", " + pincode)
            it.hideKeyboard()

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
                        showErrorSnackBar("Login Successful", false)
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra(Constants.ACCOUNTNO, accoutNo)
                        startActivity(intent)
                    }
                } catch (e: Exception) {
                    Log.e("Login", "Failure")
                }
            }
        }
    }
}