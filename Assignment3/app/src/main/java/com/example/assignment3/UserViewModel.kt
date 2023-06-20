package com.example.assignment3

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment3.Models.User
import com.example.assignment3.Utils.DBUtils

class UserViewModel: ViewModel() {

    val currentUser: MutableLiveData<User> by lazy {
        MutableLiveData<User>()
    }

    fun transferMoney(context: Context, accountNo: String, receiver: String, amount: Double) {
        DBUtils.with(context).getDB().userDao()
            .transferMoney(accountNo, receiver, amount)

        DBUtils.with(context)
    }

    fun makeFD(context: Context, accountNo: String, amount: Double) {
        DBUtils.with(context).getDB().userDao().reduceMoney(accountNo, amount)
    }
}