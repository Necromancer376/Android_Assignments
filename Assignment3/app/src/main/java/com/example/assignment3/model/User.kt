package com.example.assignment3.model

import androidx.databinding.BaseObservable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    var name: String,
    var crnNo: String,
    @PrimaryKey(autoGenerate = false) var accNo: String,
    var phoneNo: String,
    var type: String,
    var email: String,
    var ifscCode: String,
    var pincode: String = "",
    var balance: Double = 100.0
)
//    : BaseObservable() {
//    var name: String = " "
//    var crnNo: String= " "
//    var phoneNo: String= " "
//    var type: String= " "
//    var email: String= " "
//    var ifscCode: String= " "
//    var pincode: String = ""
//    var balance: Double = 100.0
//}