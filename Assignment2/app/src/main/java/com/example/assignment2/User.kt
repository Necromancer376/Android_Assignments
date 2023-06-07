package com.example.assignment2

import java.io.Serializable

data class User(
    val name: String,
    val crnNo: String,
    val accNo: String,
    val phoneNo: String,
    val type: String,
    val email: String,
    val ifscCode: String,
    var pincode: String = "",
    var balance: Double = 100.0,
    var fds: ArrayList<FD> = arrayListOf<FD>()
): Serializable
