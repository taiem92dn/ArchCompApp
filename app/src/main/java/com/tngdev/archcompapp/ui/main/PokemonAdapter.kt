package com.tngdev.archcompapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tngdev.archcompapp.databinding.ItemPokemonBinding
import com.tngdev.archcompapp.model.Pokemon

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonVH>() {

    var data : List<Pokemon>? = null

    class PokemonVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding : ItemPokemonBinding

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonVH {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = PokemonVH(binding.root)
        holder.binding = binding

        return holder
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: PokemonVH, position: Int) {
        val item = data?.get(position)
        Glide.with(holder.itemView)
            .load(item?.sprites?.frontDefault)
            .into(holder.binding.ivPkmSprite)
        holder.binding.tvPkmName.text = String.format("${item?.name} ${item?.weight}kg")
    }


}