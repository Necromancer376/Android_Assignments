package com.example.assignment4.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment4.adapter.CharacterAdapter
import com.example.assignment4.api.ServiceGenerator
import com.example.assignment4.model.Character
import kotlinx.coroutines.*

class CharacterViewModel: ViewModel() {

    private val TAG = "CharacterTAG"

    var characterList = MutableLiveData<List<Character>>()
    val adapter = CharacterAdapter()

    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }

    suspend fun setCharacterList() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = ServiceGenerator.api.getCharacters()

            if(response.isSuccessful) {
                characterList.postValue(response.body())

                withContext(Dispatchers.Main) {
                    adapter.getList(characterList.value!!)
                }
            }
            else {
                Log.e(TAG, "Failed")
            }
            characterList
        }
    }

    fun getCharacter(pos: Int): Character? {
        return characterList.value?.get(pos)
    }

    fun getImageUrl(pos: Int): String {
        return characterList.value!!.get(pos).imageurl
    }
}