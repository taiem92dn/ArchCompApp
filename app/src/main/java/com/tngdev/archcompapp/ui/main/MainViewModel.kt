package com.tngdev.archcompapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tngdev.archcompapp.model.Pokemon
import com.tngdev.archcompapp.repository.PokemonRepository

class MainViewModel : ViewModel() {
    private lateinit var pokemons : LiveData<List<Pokemon>>

    fun getPokemons() : LiveData<List<Pokemon>> {
        if (!::pokemons.isInitialized) {
            pokemons = PokemonRepository().getPokemon()
        }

        return pokemons
    }
}