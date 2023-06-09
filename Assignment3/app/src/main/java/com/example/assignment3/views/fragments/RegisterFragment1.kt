package com.example.assignment3.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModelProvider
import com.example.assignment3.Utils.Constants
import com.example.assignment3.Utils.DBUtils
import com.example.assignment3.model.User
import com.example.assignment3.R
import com.example.assignment3.UserViewModel
import com.example.assignment3.databinding.FragmentRegister1Binding
import java.lang.RuntimeException
import java.util.*


class RegisterFragment1 : BaseFragment() {

    private lateinit var binding: FragmentRegister1Binding
    private var acc_type = "Current"
    var mUser = ObservableField<User>()
    lateinit var userViewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegister1Binding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.user = userViewModel
        userViewModel.currentUser.value = User("", "", "", "", "", "", "")

        setupSpinner()

        binding.btnNextRegister.setOnClickListener {
            validateDetails(it)
        }
    }

    fun validateDetails(view: View) {
        view.hideKeyboard()

//        if (binding.etName.text!!.length < 4)
//            showErrorSnackBar(getString(R.string.error_name), true)
//        else if (binding.etAcc.text!!.length != 11)
//            showErrorSnackBar(getString(R.string.error_account), true)
//        else if (binding.etCrn.text!!.length != 9)
//            showErrorSnackBar(getString(R.string.error_crn), true)
//        else if (binding.etIfsc.text!!.length != 11)
//            showErrorSnackBar(getString(R.string.error_ifsc), true)
//        else if (binding.etPhone.text!!.length != 10)
//            showErrorSnackBar(getString(R.string.error_phone), true)
//        else if (binding.etEmail.text!!.isEmpty() || !binding.etEmail.text.toString().isValidEmail())
//            showErrorSnackBar(getString(R.string.error_email), true)
//        else {
//            addUser()
//        }
        if (userViewModel.currentUser.value!!.name.length < 4)
            showErrorSnackBar(getString(R.string.error_name), true)
        else if (userViewModel.currentUser.value!!.accNo.length != 11)
            showErrorSnackBar(getString(R.string.error_account), true)
        else if (userViewModel.currentUser.value!!.crnNo.length != 9)
            showErrorSnackBar(getString(R.string.error_crn), true)
        else if (userViewModel.currentUser.value!!.ifscCode.length != 11)
            showErrorSnackBar(getString(R.string.error_ifsc), true)
        else if (userViewModel.currentUser.value!!.phoneNo.length != 10)
            showErrorSnackBar(getString(R.string.error_phone), true)
        else if (userViewModel.currentUser.value!!.email.isEmpty() || !userViewModel.currentUser.value!!.email.isValidEmail())
            showErrorSnackBar(getString(R.string.error_email), true)
        else {
            addUser()
        }
    }

    private fun addUser() {

//        val user = User(
//            binding.etName.text.toString(),
//            binding.etCrn.text.toString(),
//            binding.etAcc.text.toString(),
//            binding.etPhone.text.toString(),
//            acc_type,
//            binding.etPhone.text.toString(),
//            binding.etIfsc.text.toString()
//        )

        val user = userViewModel.currentUser.value!!
        Log.e("User", user.toString())

        try {
            userViewModel.insertUser(requireContext(), user)

            val bundle = Bundle()
            bundle.putString(Constants.ACCOUNTNO, binding.etAcc.text.toString())

            val fragment = RegisterFragment2()
            fragment.arguments = bundle

            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(this.toString())
            transaction.commit()
        } catch (e: RuntimeException) {
            showErrorSnackBar(getString(R.string.error_account_exists), true)
        }
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter<String>(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.account_type)
        )
        binding.spinnerAccountType.adapter = adapter

        binding.spinnerAccountType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterview: AdapterView<*>?, view: View?,
                    position: Int, id: Long
                ) {
                    acc_type = adapterview?.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}