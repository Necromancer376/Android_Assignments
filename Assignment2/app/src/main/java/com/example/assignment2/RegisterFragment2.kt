package com.example.assignment2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_register2.*

class RegisterFragment2 : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)


        val user: User = arguments?.getSerializable("registerUser") as User

        tv_reg_acc.text = user.accNo
        tv_reg_crn.text = user.crnNo
        tv_reg_ifsc.text = user.ifscCode

        btn_save_user.setOnClickListener {

            it.hideKeyboard()

            if(et_pincode.text.toString().length != 6) {
                showErrorSnackBar(getString(R.string.error_pincode), true)
            }
            else if(et_pincode.text.toString() != et_confirm_pincode.text.toString()) {
                showErrorSnackBar(getString(R.string.error_confirm_pincode), true)
            }
            else {
                val accNo = user.accNo

                user.pincode = et_pincode.text.toString()

                val jsonUser: String = Gson().toJson(user)
                Log.e("user", jsonUser)

                val sharedPreferences = requireActivity()
                    .getSharedPreferences("UserPreference", Context.MODE_PRIVATE)

                val editor = sharedPreferences.edit()
                editor.apply {
                    putString(accNo.toString(), jsonUser)
                }.apply()

                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}