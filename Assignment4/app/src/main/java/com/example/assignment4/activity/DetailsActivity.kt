package com.example.assignment4.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.assignment4.databinding.ActivityDetailsBinding
import com.example.assignment4.model.Character
import com.example.assignment4.utils.Constants

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var character: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        character = intent.getSerializableExtra(Constants.CHARACTER_KEY) as Character

        var url = character.imageurl
        url = url.replace("https", "http")

        Glide.with(this@DetailsActivity)
            .load(url)
            .fitCenter()
            .into(binding.imgCharacterDetails)

        binding.character = character
    }

    fun openImageActivity(view: View) {
        val intent = Intent(this, ImageActivity::class.java)
        intent.putExtra(Constants.CHARACTER_KEY, character)
        startActivity(intent)
    }
}