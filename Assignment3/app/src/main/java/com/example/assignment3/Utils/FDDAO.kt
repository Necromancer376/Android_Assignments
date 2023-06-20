package com.example.assignment3.Utils

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.example.assignment3.Models.FD

@Dao
interface FDDAO {

    @Insert
    fun insertFd(fd: FD)

    @Delete
    fun deleteFd(fd: FD)
}