package com.example.assignment3

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*


data class FD(
    val accountNumber: String,
    val amount: Double,
    val duration: Int,
    val rate: Double,
    var date: String,
    val id: UUID = UUID.randomUUID()
)
