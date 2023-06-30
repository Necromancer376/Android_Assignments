package com.example.assignment4.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {

    private val URL = "http://simplifiedcoding.net/"
//    private val URL = "https://marvelapi.loca.lt/"

    val api = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}