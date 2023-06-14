package com.example.assignment3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.assignment3.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, RegisterFragment1())
        transaction.addToBackStack(null)
        transaction.commit()
    }
}