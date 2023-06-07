package com.example.assignment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class SigninFragmentAdapter(fragmenrManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmenrManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            1 -> LoginFragment()
            2 -> FSAFragment()
            else -> RegisterFragment()
        }
    }
}