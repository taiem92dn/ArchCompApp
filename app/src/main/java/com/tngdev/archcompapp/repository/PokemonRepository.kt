package com.tngdev.archcompapp.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.tngdev.archcompapp.db.dao.PokemonDao
import com.tngdev.archcompapp.model.ListResponse
import com.tngdev.archcompapp.model.PkmItem
import com.tngdev.archcompapp.model.Pokemon
import com.tngdev.archcompapp.network.ApiResource
import com.tngdev.archcompapp.network.PokeService
import com.tngdev.archcompapp.util.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val pokemonService: PokeService,
    private val pokemonDao: PokemonDao
){
    private val TAG = PokemonRepository::class.java.simpleName

    @Inject lateinit var application: Application


    fun getListPokemon(): LiveData<ApiResource<LiveData<List<Pokemon>>>> {
        val result = MutableLiveData<ApiResource<LiveData<List<Pokemon>>>>()

        updateListPokemonRemote(result)

        return result
    }

    fun updateListPokemonRemote(result: LiveData<ApiResource<LiveData<List<Pokemon>>>>) {
        if (result !is MutableLiveData)
            return

        val data = pokemonDao.getAll()
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
        if (!Utils.hasInternet(application)) {
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
                        pokemonDao.insert(it)
                    }
                }
                    ?: let {
                        Log.e(TAG, "onResponse: null data response getPokemon $name")
                    }
            }

        })
    }
}

