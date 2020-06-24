package com.tngdev.archcompapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tngdev.archcompapp.model.ListResponse
import com.tngdev.archcompapp.model.Pokemon
import com.tngdev.archcompapp.network.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonRepository {

    private val pokemonService = RetrofitApi.pokeService

    fun getPokemon() : LiveData<List<Pokemon>> {
        val data = MutableLiveData<List<Pokemon>>()
        pokemonService.listPokemon(20, 0).enqueue(object : Callback<ListResponse<Pokemon>> {
            override fun onFailure(call: Call<ListResponse<Pokemon>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ListResponse<Pokemon>>,
                response: Response<ListResponse<Pokemon>>
            ) {
                data.value = response.body()?.results
            }

        })
        
        return data
    }
}

