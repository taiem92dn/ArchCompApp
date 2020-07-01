package com.tngdev.archcompapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tngdev.archcompapp.model.ListResponse
import com.tngdev.archcompapp.model.Pokemon
import com.tngdev.archcompapp.network.ApiResource
import com.tngdev.archcompapp.network.RetrofitApi
import com.tngdev.archcompapp.util.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonRepository {

    private val pokemonService = RetrofitApi.pokeService

    fun getPokemon(): LiveData<ApiResource<List<Pokemon>>> {
        val data = MutableLiveData<ApiResource<List<Pokemon>>>()

        data.value = ApiResource.Loading(null)
        if (!Utils.hasInternet()) {
            data.value = ApiResource.NoInternet(null)
        }
        else {
            pokemonService.listPokemon(20, 0).enqueue(object : Callback<ListResponse<Pokemon>> {
                override fun onFailure(call: Call<ListResponse<Pokemon>>, t: Throwable) {
                    data.value = ApiResource.Error(t.message?: "Unknown Error", null)
                }

                override fun onResponse(
                    call: Call<ListResponse<Pokemon>>,
                    response: Response<ListResponse<Pokemon>>
                ) {
                    data.value = response.body()?.let {
                        ApiResource.Success(it.results)
                    }
                        ?: let {
                            Log.e("PokemonRepository", "onResponse: null data response", null)
                            ApiResource.Error<List<Pokemon>>("Error wrong data", null)
                        }

                }

            })
        }

        return data
    }
}

