package com.example.assignment3

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDAO {

    @Insert
    fun insertUser(user:User)

    @Delete
    fun deleteUser(user:User)

    @Query("SELECT * FROM users WHERE accNo = :acc")
    fun getUser(acc: String): LiveData<User>

    @Query("UPDATE users SET pincode = :pin WHERE accNo = :acc")
    fun setPincode(acc: String, pin: String)

    @Query("UPDATE users SET balance = :balance WHERE accNo = :acc")
    fun updateBalance(acc: String, balance: Double)

    @Query("UPDATE users SET balance = balance - :amount WHERE accNo = :accSend")
    fun sendMoney(accSend: String, amount: Double)

    @Query("UPDATE users SET balance = balance + :amount WHERE accNo = :accRecv")
    fun receiveMoney(accRecv: String, amount: Double)

    fun transferMoney(accSend: String, accRecv: String, amount: Double) =
        run {
            sendMoney(accSend, amount)
            receiveMoney(accRecv, amount)
        }
}