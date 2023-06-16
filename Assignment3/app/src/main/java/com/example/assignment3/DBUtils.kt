package com.example.assignment3

import android.content.Context
import androidx.room.Room

class DBUtils {
    companion object {
        private var dbUtil = DBUtils()
        private lateinit var database: UserDB

        fun with(context: Context): DBUtils {
            dbUtil = DBUtils(context)
            return dbUtil
        }
    }

    constructor()

    constructor(context: Context) {
        database = Room.databaseBuilder(
            context,
            UserDB::class.java, "BankDB"
        )
            .allowMainThreadQueries()
            .build()
    }

    fun getDB(): UserDB {
        return database
    }
}