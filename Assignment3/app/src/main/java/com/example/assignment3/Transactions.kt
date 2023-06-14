package com.example.assignment3

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*

@Entity(tableName = "transactions")
data class Transactions (
    val accountSender: String,
    val accountReceiver: String,
    val amount: Double,
    var date: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)