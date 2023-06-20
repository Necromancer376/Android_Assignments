package com.example.assignment3.Utils

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assignment3.Models.User
import com.example.assignment3.Utils.UserDAO

@Database(
    entities = [User::class],
    version = 1
)
abstract class UserDB: RoomDatabase() {
    abstract fun userDao(): UserDAO
}