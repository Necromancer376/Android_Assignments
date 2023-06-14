package com.example.assignment3

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

@Dao
interface FDDAO {

    @Insert
    fun insertFd(fd: FD)

    @Delete
    fun deleteFd(fd: FD)
}