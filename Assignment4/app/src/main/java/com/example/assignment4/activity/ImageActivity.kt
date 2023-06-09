package com.example.assignment4.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.assignment4.databinding.ActivityImageBinding
import com.example.assignment4.model.Character
import com.example.assignment4.utils.Constants
import com.example.assignment4.viewmodel.CharacterViewModel

class ImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageBinding
    private lateinit var viewModel: CharacterViewModel
    private lateinit var character: Character
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CharacterViewModel::class.java]

        character = intent.getSerializableExtra(Constants.CHARACTER_KEY) as Character
//        position = intent.getIntExtra(Constants.CHARACTER_KEY, 0)

        loadImage()
        binding.character = character
    }

    fun loadImage() {
        val url = Constants.formatUrl(character.imageurl)

        Glide.with(this)
            .load(url)
            .fitCenter()
            .into(binding.imgCharacterActivity)
    }
}