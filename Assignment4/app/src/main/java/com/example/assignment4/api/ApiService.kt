package com.example.assignment4.api

import com.example.assignment4.model.Character
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("demos/marvel")
    suspend fun getCharacters(): Response<List<Character>>
}