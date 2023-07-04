package com.example.assignment5.contracts

import com.example.assignment5.network.model.Character

interface Contracts {
    interface CharacterView {
        fun init()
        fun onError(message: String?)
        fun loadDataInList(characterList: List<Character>)
    }

    interface Presenter {
        fun onStart()
        fun getData()
    }
}