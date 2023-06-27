package com.example.assignment3.model

import java.util.*

data class Transactions (
    val sender: String,
    val receiver: String,
    val amount: Double,
    var date: String,
    val id: UUID = UUID.randomUUID()
)