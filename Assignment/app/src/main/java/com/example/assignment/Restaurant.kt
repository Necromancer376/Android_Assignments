package com.example.components2

class Restaurant(val name: String,
                 val type: String,
                 val rating: String,
                 val authority: String,
                 val email: String,
                 val score: Array<String>,
                 val website: String,
                 val longitude: String,
                 val latitude: String,
                 var expandable: Boolean = false,
) {
}