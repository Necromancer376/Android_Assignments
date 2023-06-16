package com.example.assignment3

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*

data class Transactions (
    val sender: String,
    val receiver: String,
    val amount: Double,
    var date: String,
    val id: UUID = UUID.randomUUID()
)