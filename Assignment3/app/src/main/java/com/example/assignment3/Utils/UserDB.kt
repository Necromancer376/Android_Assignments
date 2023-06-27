package com.example.assignment3.Utils

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assignment3.model.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class UserDB: RoomDatabase() {
    abstract fun userDao(): UserDAO
}