package com.example.assignment4.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment4.activity.DetailsActivity
import com.example.assignment4.activity.ImageActivity
import com.example.assignment4.databinding.ItemCharacterMainBinding
import com.example.assignment4.model.Character
import com.example.assignment4.utils.Constants

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.MyViewHolder>() {

    private var list = listOf<Character>()
    private lateinit var context: Context
    private lateinit var binding: ItemCharacterMainBinding

    fun getList(list: List<Character>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class MyViewHolder(val binding: ItemCharacterMainBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = ItemCharacterMainBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        context = parent.context
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = this.list[position]
        holder.binding.character = item

        var url = item.imageurl
        url = url.replace("https", "http")

        Glide.with(holder.itemView)
            .load(url)
            .fitCenter()
            .into(holder.binding.imgCharacter)

        holder.binding.imgCharacter.setOnClickListener {
            val intent = Intent(context, ImageActivity::class.java)
            intent.putExtra(Constants.CHARACTER_KEY, item)
//            intent.putExtra(Constants.CHARACTER_KEY, position)
            context.startActivity(intent)
        }

        holder.binding.llText.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(Constants.CHARACTER_KEY, item)
//            intent.putExtra(Constants.CHARACTER_KEY, position)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}