package com.example.assignment2

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_register.*
import java.io.Serializable
import java.util.*


class RegisterFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

//        var randomGenerator = Random(System.currentTimeMillis())

        val crn = (10000000..99999999).random()
        val acc = (10000000000..99999999999).random()
        val ifsc = (10000000000..99999999999).random()
        var acc_type = "Current"

        // Next
        btn_next_register.setOnClickListener {

            it.hideKeyboard()

            if(et_name.text!!.length < 4)
                showErrorSnackBar(getString(R.string.error_name), true)

            else if(et_phone.text!!.length != 10)
                showErrorSnackBar(getString(R.string.error_phone), true)

            else if(et_email.text!!.isEmpty() || !et_email.text.toString().isValidEmail())
                showErrorSnackBar(getString(R.string.error_email), true)

            else {

                val user = User(
                    et_name.text.toString(),
                    crn.toString(),
                    acc.toString(),
                    et_phone.text.toString(),
                    acc_type,
                    et_email.text.toString(),
                    ifsc.toString()
                )

                val bundle = Bundle()
                bundle.putSerializable("registerUser", user)

                val fragment = RegisterFragment2()
                fragment.arguments = bundle

                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, fragment)
                transaction.commit()
            }
        }

        //Spinner
        val adapter = ArrayAdapter<String>(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.account_type)
        )
        spinner_account_type.adapter = adapter

        spinner_account_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterview: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                acc_type = adapterview?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }
    }

    fun String.isValidEmail() =
        !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}