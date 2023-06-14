package com.example.assignment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : BaseFragment() {

    private var acc_type = "Current"

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

//        val crn = (10000000..99999999).random()
//        val acc = (10000000000..99999999999).random()
//        val ifsc = (10000000000..99999999999).random()

        // Next
        btn_next_register.setOnClickListener {

            it.hideKeyboard()

        }
        if(et_name.text!!.length < 4)
            showErrorSnackBar(getString(R.string.error_name), true)

        else if(et_acc.text!!.length != 11)
            showErrorSnackBar(getString(R.string.error_account), true)

        else if(et_crn.text!!.length != 9)
            showErrorSnackBar(getString(R.string.error_crn), true)

        else if(et_ifsc.text!!.length != 11)
            showErrorSnackBar(getString(R.string.error_ifsc), true)

        else if(et_phone.text!!.length != 10)
            showErrorSnackBar(getString(R.string.error_phone), true)

        else if(et_email.text!!.isEmpty() || !et_email.text.toString().isValidEmail())
            showErrorSnackBar(getString(R.string.error_email), true)

        else {
            addUser()
        }

        setupSpinner()
    }

    private fun setupSpinner() {

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

    private fun addUser() {
        val user = User(
            et_name.text.toString(),
            et_crn.text.toString(),
            et_acc.text.toString(),
            et_phone.text.toString(),
            acc_type,
            et_email.text.toString(),
            et_ifsc.text.toString()
        )

        val bundle = Bundle()
        bundle.putSerializable("registerUser", user)

        val fragment = RegisterFragment2()
        fragment.arguments = bundle

        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(this.toString())
        transaction.commit()
    }
}