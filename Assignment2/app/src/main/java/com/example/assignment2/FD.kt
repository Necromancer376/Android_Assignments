package com.example.assignment2

import java.io.Serializable
import java.util.*

data class FD(
    val accountNumber: String,
    val amount: Double,
    val duration: Int,
    val rate: Double,
    val date: Date
): Serializable
