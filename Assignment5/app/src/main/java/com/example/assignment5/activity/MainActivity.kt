package com.example.assignment5.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.assignment5.R
import com.example.assignment5.adapter.CharacterAdapter
import com.example.assignment5.contracts.Contracts
import com.example.assignment5.databinding.ActivityMainBinding
import com.example.assignment5.network.model.Character
import com.example.assignment5.presenter.CharacterPresenter

class MainActivity : AppCompatActivity(), Contracts.CharacterView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: CharacterPresenter
    private var adapter = CharacterAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = CharacterPresenter(this)
        presenter.onStart()
    }

    override fun init() {
        presenter.getData()
    }

    override fun onError(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun loadDataInList(characterList: List<Character>) {
        adapter.getList(characterList)
        binding.rvMain.adapter = adapter
    }
}