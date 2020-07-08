package com.tngdev.archcompapp.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tngdev.archcompapp.model.Pokemon
import com.tngdev.archcompapp.network.ApiResource
import com.tngdev.archcompapp.repository.PokemonRepository

class PokemonListViewModel @ViewModelInject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    private val pokemons : LiveData<ApiResource<LiveData<List<Pokemon>>>> by lazy {
        repository.getListPokemon()
    }

    fun getListPokemon() : LiveData<ApiResource<LiveData<List<Pokemon>>>> {
//        if (!::pokemons.isInitialized) {
//            pokemons = repository.getListPokemon()
//        }

        return pokemons
    }

    fun refreshData() {
        repository.updateListPokemonRemote(pokemons)
    }


}