package com.example.assignment

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.fragment.app.FragmentManager
import com.example.assignment.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val tabTitleList = arrayOf("Register", "Login", "Food Standards Agency")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        val fragmentManager: FragmentManager = supportFragmentManager
        val adapter = SigninFragmentAdapter(fragmentManager, lifecycle)
        viewpager_login_activity.adapter = adapter

        TabLayoutMediator(tab_login_activity, viewpager_login_activity) { tab, position ->
            tab.text = tabTitleList[position]
        }.attach()
    }
}