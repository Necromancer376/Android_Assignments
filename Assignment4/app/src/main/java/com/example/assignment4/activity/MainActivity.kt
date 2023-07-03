package com.example.assignment4.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.assignment4.adapter.CharacterAdapter
import com.example.assignment4.databinding.ActivityMainBinding
import com.example.assignment4.viewmodel.CharacterViewModel
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CharacterViewModel::class.java]
        runBlocking {
            viewModel.setCharacterList()
        }

        binding.rvMain.adapter = viewModel.adapter
    }
}