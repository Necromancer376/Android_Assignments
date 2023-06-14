package com.example.assignment3

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    val name: String,
    val crnNo: String,
    @PrimaryKey(autoGenerate = false) val accNo: String,
    val phoneNo: String,
    val type: String,
    val email: String,
    val ifscCode: String,
    var pincode: String = "",
    var balance: Double = 100.0
)