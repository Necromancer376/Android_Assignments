package com.example.assignment3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.room.Room
import com.example.assignment3.databinding.FragmentRegister1Binding


class RegisterFragment1 : BaseFragment() {

    private lateinit var binding: FragmentRegister1Binding
    private lateinit var database: UserDB
    private var acc_type = "Current"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegister1Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        binding.btnNextRegister.setOnClickListener {
            it.hideKeyboard()

            if (binding.etName.text!!.length < 4)
                showErrorSnackBar(getString(R.string.error_name), true)
            else if (binding.etAcc.text!!.length != 11)
                showErrorSnackBar(getString(R.string.error_phone), true)
            else if (binding.etCrn.text!!.length != 9)
                showErrorSnackBar(getString(R.string.error_phone), true)
            else if (binding.etIfsc.text!!.length != 11)
                showErrorSnackBar(getString(R.string.error_phone), true)
            else if (binding.etPhone.text!!.length != 10)
                showErrorSnackBar(getString(R.string.error_phone), true)
            else if (binding.etEmail.text!!.isEmpty() || !binding.etEmail.text.toString()
                    .isValidEmail()
            )
                showErrorSnackBar(getString(R.string.error_email), true)
            else {
                addUser()
            }
        }
        setupSpinner()
    }

    private fun addUser() {
        database = Room.databaseBuilder(
            requireContext(),
            UserDB::class.java, "users"
        )
            .allowMainThreadQueries()
            .build()

        val user = User(
            binding.etName.text.toString(),
            binding.etCrn.text.toString(),
            binding.etAcc.text.toString(),
            binding.etPhone.text.toString(),
            acc_type,
            binding.etPhone.text.toString(),
            binding.etIfsc.text.toString()
        )

        database.userDao().insertUser(user)
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
}