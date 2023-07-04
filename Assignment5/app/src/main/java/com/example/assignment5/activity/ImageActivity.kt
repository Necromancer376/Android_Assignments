package com.example.assignment5.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.assignment5.databinding.ActivityImageBinding
import com.example.assignment5.network.model.Character
import com.example.assignment5.utils.Constants

class ImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageBinding
    private lateinit var character: Character


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        character = intent.getSerializableExtra(Constants.CHARACTER_KEY) as Character

        binding.character = character

        Glide.with(this)
            .load(Constants.formatUrl(character.imageurl))
            .fitCenter()
            .into(binding.imgCharacterActivity)
    }
}