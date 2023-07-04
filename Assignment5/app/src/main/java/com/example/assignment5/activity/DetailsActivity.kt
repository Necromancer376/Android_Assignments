package com.example.assignment5.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.assignment5.databinding.ActivityDetailsBinding
import com.example.assignment5.utils.Constants
import com.example.assignment5.network.model.Character

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var character: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        character = intent.getSerializableExtra(Constants.CHARACTER_KEY) as Character

        binding.character = character

        Glide.with(this)
            .load(Constants.formatUrl(character.imageurl))
            .fitCenter()
            .into(binding.imgCharacterDetails)
    }

    fun openImageActivity(view: View) {
        val intent = Intent(this, ImageActivity::class.java)
        intent.putExtra(Constants.CHARACTER_KEY, character)
        startActivity(intent)
    }
}