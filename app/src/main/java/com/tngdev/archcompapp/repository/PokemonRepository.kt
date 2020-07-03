package com.tngdev.archcompapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.tngdev.archcompapp.db.PokemonDB
import com.tngdev.archcompapp.model.ListResponse
import com.tngdev.archcompapp.model.PkmItem
import com.tngdev.archcompapp.model.Pokemon
import com.tngdev.archcompapp.network.ApiResource
import com.tngdev.archcompapp.network.RetrofitApi
import com.tngdev.archcompapp.util.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class PokemonRepository {
    private val TAG = PokemonRepository::class.java.simpleName

    private val pokemonService = RetrofitApi.pokeService

    fun getListPokemon(): LiveData<ApiResource<LiveData<List<Pokemon>>>> {
        val result = MutableLiveData<ApiResource<LiveData<List<Pokemon>>>>()

        val data = PokemonDB.appDatabase.pokemonDao().getAll()
        val limit = 20
        val checkResult = MutableLiveData<Int>(0)
        checkResult.observeForever(object : Observer<Int> {
            override fun onChanged(t: Int?) {
                t ?.let {
                    if (it == limit) {
                        result.value = ApiResource.Success(data)
                        checkResult.removeObserver(this)
                    }
                }
            }

        })

        result.value = ApiResource.Loading(data)
        if (!Utils.hasInternet()) {
            result.value = ApiResource.NoInternet(data)
        }
        else {
            pokemonService.listPokemon(limit, 0).enqueue(object : Callback<ListResponse<PkmItem>> {
                override fun onFailure(call: Call<ListResponse<PkmItem>>, t: Throwable) {
                    result.value = ApiResource.Error(t.message?: "Unknown Error", data)
                }

                override fun onResponse(
                    call: Call<ListResponse<PkmItem>>,
                    response: Response<ListResponse<PkmItem>>
                ) {
                    response.body()?.let {
                        getListPokemon(it.results, checkResult)
                    }
                        ?: let {
                            Log.e("PokemonRepository", "onResponse: null data response", null)
                            ApiResource.Error("Error wrong data", data)
                        }

                }

            })
        }

        return result
    }

    private fun getListPokemon(pkmItems: List<PkmItem>, checkResult: MutableLiveData<Int>) {
        for (item in pkmItems) {
            item.name?. let {
                getPokemon(it, checkResult)
            }
        }
    }

    private fun getPokemon(name : String, checkResult: MutableLiveData<Int>) {
        pokemonService.getPokemon(name).enqueue(object : Callback<Pokemon> {
            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Log.e(TAG, "onFailure: getPokemon $name")
                checkResult.value = checkResult.value?.plus(1)
            }

            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                response.body()?.let {
                    checkResult.value = checkResult.value?.plus(1)
                    Executors.newSingleThreadExecutor().execute {
                        PokemonDB.appDatabase.pokemonDao().insert(it)
                    }
                }
                    ?: let {
                        Log.e(TAG, "onResponse: null data response getPokemon $name")
                    }
            }

        })
    }
}

