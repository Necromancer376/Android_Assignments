package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.assignment.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = requireActivity().findViewById<TabLayout>(R.id.tab_login_activity)

//        tv_register.setOnClickListener {
//            val tab = tabLayout.getTabAt(1)
//            tab?.select()
//        }

        btn_login.setOnClickListener {
            if(et_email.text!!.isEmpty() || !et_email.text.toString().isValidEmail())
                showErrorSnackBar("Fill the Email", true)

            else if(et_password.text!!.isEmpty())
                showErrorSnackBar("Fill Password", true)

            else {
                showErrorSnackBar("Login Success", false)
            }
        }
    }

    fun String.isValidEmail() =
        !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}