package com.example.assignment2

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*

class MainActivity : AppCompatActivity() {

    lateinit var user: User
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var accountNo: String

    lateinit var header_profile: ImageView
    lateinit var header_name: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setTitle("")

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        accountNo = intent.getStringExtra(Constants.ACCNO) as String

        val json = PrefUtils.with(this).getUser(accountNo)
        user = Gson().fromJson(json, User::class.java)

        showInfo()

        btn_transfer.setOnClickListener {
            val intent = Intent(this@MainActivity, TransferMoneyActivity::class.java)
            intent.putExtra(Constants.USERACC, user.accNo)
            startActivity(intent)
        }

        btn_fd.setOnClickListener {
            val intent = Intent(this@MainActivity, FDActivity::class.java)
            intent.putExtra(Constants.USERACC, user.accNo)
            startActivity(intent)
        }

        val navHeader = nav_view.getHeaderView(0)
        header_profile = navHeader.findViewById<ImageView>(R.id.img_nav_profile)
        header_name = navHeader.findViewById<TextView>(R.id.tv_nav_header_name)

        header_name.text = user.name.toString()

        header_profile.setOnClickListener {
            pickImage()
        }

        nav_view.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.mItem2 -> {
                    val intent = Intent(this@MainActivity, TransferMoneyActivity::class.java)
                    intent.putExtra(Constants.USERACC, user.accNo)
                    startActivity(intent)
                }
                R.id.mItem3 -> {
                    val intent = Intent(this@MainActivity, FDActivity::class.java)
                    intent.putExtra(Constants.USERACC, user.accNo)
                    startActivity(intent)
                }
            }
            true
        }

        toggle = ActionBarDrawerToggle(
            this@MainActivity, home_layout,
            R.string.open, R.string.close
        )
        home_layout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()

        val json = PrefUtils.with(this).getUser(accountNo)
        user = Gson().fromJson(json, User::class.java)

        showInfo()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showInfo() {
        tv_home_acc.text = user.accNo
        tv_home_name.text = user.name
        tv_home_crn.text = user.crnNo
        tv_home_balance.text = user.balance.toString()
    }


    private fun pickImage() {
        var intent = Intent("android.provider.action.PICK_IMAGES")
        startActivityForResult(intent, 101)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == 101) {
                val uri = data?.data
                img_nav_profile.setImageURI(uri)
            }
        }
    }
}