package com.example.assignment3.model

import java.util.*


data class FD(
    val accountNumber: String,
    val amount: Double,
    val duration: Int,
    val rate: Double,
    var date: String,
    val id: UUID = UUID.randomUUID()
)
