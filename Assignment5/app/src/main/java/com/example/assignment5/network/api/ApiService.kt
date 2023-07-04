package com.example.assignment5.network.api

import com.example.assignment5.network.model.Character
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface ApiService {

    @GET("demos/marvel")
    fun getCharacters(): Observable<List<Character>>
}