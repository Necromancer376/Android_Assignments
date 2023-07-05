package com.example.assignment3

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.assignment3.model.User
import com.example.assignment3.Utils.DBUtils
import com.example.assignment3.Utils.DownloadWorker
import com.example.assignment3.model.FD
import com.example.assignment3.model.Transactions

class UserViewModel: ViewModel(), Observable {

    val currentUser: MutableLiveData<User> by lazy {
        MutableLiveData<User>()
    }
//
//    val currentUser = MutableLiveData<User>()

    fun transferMoney(context: Context, accountNo: String, receiver: String, amount: Double) {
        DBUtils.with(context).getDB().userDao()
            .transferMoney(accountNo, receiver, amount)

        DBUtils.with(context)
    }

    fun makeFD(context: Context, accountNo: String, amount: Double) {
        DBUtils.with(context).getDB().userDao().reduceMoney(accountNo, amount)
    }

    fun getUser(context: Context, accountNo: String): LiveData<List<User>> {
        return DBUtils.with(context).getDB().userDao().getUser(accountNo)
    }

    fun setPincode(context: Context, accountNo: String, pincode: String) {
        DBUtils.with(context).getDB().userDao().setPincode(accountNo, pincode)
    }

    fun getBalance(context: Context, accountNo: String): Double {
        return DBUtils.with(context).getDB().userDao().getBalance(accountNo)
    }


    fun saveReceiptDialog(context: Context, transaction: Transactions) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(context.getString(R.string.download_message_fd))
        builder.setTitle(context.getString(R.string.download_receipt))
        builder.setCancelable(false)

        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener { dialogInterface, i ->
            Log.e("Dialog", "Yes")

            val downloadWorkRequest = OneTimeWorkRequest.Builder(DownloadWorker::class.java)
            val data = Data.Builder()
            val filename = "transaction_" + transaction.id.toString() + ".txt"
            data.putString(context.getString(R.string.file_content), transaction.toString())
            data.putString(context.getString(R.string.file_name), filename)
            downloadWorkRequest.setInputData(data.build())

            WorkManager.getInstance(context).enqueue(downloadWorkRequest.build())

            dialogInterface.cancel()
        }))

        builder.setNegativeButton("No", (DialogInterface.OnClickListener { dialogInterface, i ->
            Log.e("Dialog", "Yes")
            dialogInterface.cancel()
        }))

        val downloadDialog = builder.create()
        downloadDialog.show()
    }

    fun saveReceiptDialog(context: Context, fd: FD) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(context.getString(R.string.download_message_transaction))
        builder.setTitle(context.getString(R.string.download_receipt))
        builder.setCancelable(false)
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener { dialogInterface, i ->
            Log.e("Dialog", "Yes")

            val downloadWorkRequest = OneTimeWorkRequest.Builder(DownloadWorker::class.java)
            val data = Data.Builder()
            val filename = "fd_" + fd.id.toString() + ".txt"
            data.putString(context.getString(R.string.file_content), fd.toString())
            data.putString(context.getString(R.string.file_name), filename)
            downloadWorkRequest.setInputData(data.build())

            WorkManager.getInstance(context).enqueue(downloadWorkRequest.build())

            dialogInterface.cancel()
        }))

        builder.setNegativeButton("No", (DialogInterface.OnClickListener { dialogInterface, i ->
            Log.e("Dialog", "Yes")
            dialogInterface.cancel()
        }))

        val downloadDialog = builder.create()
        downloadDialog.show()
    }


    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry()}

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }
}