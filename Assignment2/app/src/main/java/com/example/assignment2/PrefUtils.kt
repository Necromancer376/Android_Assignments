package com.example.assignment2

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class PrefUtils {

    companion object {
        private var prefUtils = PrefUtils()
        private lateinit var preferences: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor

        fun with(context: Context): PrefUtils {
            prefUtils = PrefUtils(context)
            return prefUtils
        }
    }

    constructor()

    constructor(context: Context) {
        preferences = context.getSharedPreferences(
            Constants.UserPreference,
            AppCompatActivity.MODE_PRIVATE
        )
        editor = preferences.edit()
    }

    private class Builder(val context: Context) {
        fun build(): PrefUtils {
            return PrefUtils(context)
        }
    }

    fun getUser(acc: String): String? {
        return preferences.getString(acc, null)
    }

    fun saveUser(user: User) {
        val jsonUser: String = Gson().toJson(user)
        Log.e("user", jsonUser)

        editor.apply {
            putString(user.accNo, jsonUser)
        }.apply()
    }

    fun transferMoney(user1: User, user2: User) {
        val jsonUser1: String = Gson().toJson(user1)
        val jsonUser2: String = Gson().toJson(user2)

        editor.apply {
            putString(user1.accNo, jsonUser1)
            putString(user2.accNo, jsonUser2)
        }.apply()
    }
}