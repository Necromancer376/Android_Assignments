package com.example.assignment3.Utils

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.assignment3.model.User

@Dao
interface UserDAO {

    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM users WHERE accNo = :acc")
    fun getUser(acc: String): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE accNo = :acc AND pincode = :pin")
    fun loginValidation(acc: String, pin: String): User

    @Query("UPDATE users SET pincode = :pin WHERE accNo = :acc")
    fun setPincode(acc: String, pin: String)

    @Query("SELECT balance FROM users WHERE accNo = :acc")
    fun getBalance(acc: String): Double

    @Query("UPDATE users SET balance = :balance WHERE accNo = :acc")
    fun updateBalance(acc: String, balance: Double)

    @Query("UPDATE users SET balance = balance - :amount WHERE accNo = :accSend")
    fun reduceMoney(accSend: String, amount: Double)

    @Query("UPDATE users SET balance = balance + :amount WHERE accNo = :accReceive")
    fun addMoney(accReceive: String, amount: Double)

    fun transferMoney(accSend: String, accRecv: String, amount: Double) =
        run {
            reduceMoney(accSend, amount)
            addMoney(accRecv, amount)
        }
}