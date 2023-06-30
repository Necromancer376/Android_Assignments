package com.example.assignment4.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.assignment4.databinding.ActivityImageBinding
import com.example.assignment4.model.Character
import com.example.assignment4.utils.Constants

class ImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageBinding
    private lateinit var character: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        character = intent.getSerializableExtra(Constants.CHARACTER_KEY) as Character


        var url = character.imageurl
        url = url.replace("https", "http")

        Glide.with(this@ImageActivity)
            .load(url)
            .fitCenter()
            .into(binding.imgCharacterActivity)

        binding.character = character

    }
}