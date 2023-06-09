package com.example.assignment4.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.assignment4.databinding.ActivityDetailsBinding
import com.example.assignment4.model.Character
import com.example.assignment4.utils.Constants
import com.example.assignment4.viewmodel.CharacterViewModel

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var viewModel: CharacterViewModel
    private lateinit var character: Character
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CharacterViewModel::class.java]

        character = intent.getSerializableExtra(Constants.CHARACTER_KEY) as Character
//        position = intent.getIntExtra(Constants.CHARACTER_KEY, 0)

        loadImage()

        binding.character = character
    }

    private fun loadImage() {
        val url = Constants.formatUrl(character.imageurl)

        Glide.with(this)
            .load(url)
            .fitCenter()
            .into(binding.imgCharacterDetails)
    }


    fun openImageActivity(view: View) {
        val intent = Intent(this, ImageActivity::class.java)
        intent.putExtra(Constants.CHARACTER_KEY, character)
//        intent.putExtra(Constants.CHARACTER_KEY, position)
        startActivity(intent)
    }
}