package com.example.assignment3

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assignment3.databinding.FragmentRegister2Binding
import kotlinx.android.synthetic.main.fragment_register2.view.*


class RegisterFragment2 : BaseFragment() {

    private lateinit var binding: FragmentRegister2Binding
    private lateinit var accountNumber: String

//    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegister2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountNumber = arguments?.getString(Constants.ACCOUNTNO, "").toString()

//        DBUtils.with(requireContext()).getDB().userDao()
//            .getUser(accountNumber!!)
//            .observe(requireActivity()) {
//                user = it
//            }

        binding.btnSaveUser.setOnClickListener {
            it.hideKeyboard()

            if(binding.etPincode.text.toString().length != 6) {
                showErrorSnackBar(getString(R.string.error_pincode), true)
            }
            else if(binding.etPincode.text.toString() != binding.etConfirmPincode.text.toString()) {
                showErrorSnackBar(getString(R.string.error_confirm_pincode), true)
            }
            else {
                registerUser()
            }
        }
    }

    private fun registerUser() {
        val pincode = binding.etPincode.text.toString()
        DBUtils.with(requireContext()).getDB().userDao().setPincode(accountNumber!!, pincode)

        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
    }
}