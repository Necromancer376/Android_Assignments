package com.example.assignment3

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
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.assignment3.databinding.ActivityLoginBinding
import com.example.assignment3.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var userViewModel: UserViewModel
    lateinit var accountNo: String

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var header_profile: ImageView
    lateinit var header_name: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(layoutInflater)
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

        accountNo = intent.getStringExtra(Constants.ACCOUNTNO)!!
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        DBUtils.with(this).getDB().userDao().getUser(accountNo).observe(this) {
            userViewModel.currentUser.value = it.first()

            binding.viewModel = userViewModel
            setupHeader()
        }

        binding.btnFd.setOnClickListener {
            val intent = Intent(this@MainActivity, FDActivity::class.java)
            intent.putExtra(Constants.ACCOUNTNO, accountNo)
            startActivity(intent)
        }

        binding.btnTransfer.setOnClickListener {
            val intent = Intent(this@MainActivity, TransferMoneyActivity::class.java)
            intent.putExtra(Constants.ACCOUNTNO, accountNo)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        DBUtils.with(this).getDB().userDao().getUser(accountNo).observe(this) {
            userViewModel.currentUser.value = it.first()

            binding.viewModel = userViewModel
            setupHeader()
        }
    }

    private fun setupHeader() {
        val navHeader = binding.navView.getHeaderView(0)
        header_profile = navHeader.findViewById<ImageView>(R.id.img_nav_profile)
        header_name = navHeader.findViewById<TextView>(R.id.tv_nav_header_name)

        header_name.text = userViewModel.currentUser.value!!.name.toString()

        header_profile.setOnClickListener {
            pickImage()
        }

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.mItem2 -> {
                    val intent = Intent(this@MainActivity, TransferMoneyActivity::class.java)
                    intent.putExtra(Constants.ACCOUNTNO, accountNo)
                    startActivity(intent)
                }
                R.id.mItem3 -> {
                    val intent = Intent(this@MainActivity, FDActivity::class.java)
                    intent.putExtra(Constants.ACCOUNTNO, accountNo)
                    startActivity(intent)
                }
            }
            true
        }

        toggle = ActionBarDrawerToggle(
            this@MainActivity, binding.homeLayout,
            R.string.open, R.string.close
        )
        binding.homeLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    private fun pickImage() {
        ImageBottomSheet(this)
            .show(supportFragmentManager, "newTaskTag")
    }
}