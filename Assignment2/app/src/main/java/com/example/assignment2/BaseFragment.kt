package com.example.assignment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

open class BaseFragment : Fragment() {

    fun showErrorSnackBar(msg: String, errorMsg: Boolean) {
        val snackbar = Snackbar.make(requireActivity().findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view

        if(errorMsg) {
            snackbarView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorSnackbarError
                )
            )
        }
        else {
            snackbarView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorSnackbarSuccess
                )
            )
        }
        snackbar.show()
    }
}