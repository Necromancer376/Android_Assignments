package com.example.assignment4.utils

object Constants {

    const val CHARACTER_KEY = "CHARACTER_KEY"

    fun formatUrl(url: String): String {
        return url.replace("https", "http")
    }
}