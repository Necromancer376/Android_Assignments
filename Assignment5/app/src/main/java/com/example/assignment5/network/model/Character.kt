package com.example.assignment5.network.model

import java.io.Serializable

data class Character(
    val name: String,
    val realname: String,
    val team: String,
    val firstappearance: String,
    val createdby: String,
    val publisher: String,
    val imageurl: String,
    val bio: String,
): Serializable