package com.example.assignment2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, RegisterFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }
}