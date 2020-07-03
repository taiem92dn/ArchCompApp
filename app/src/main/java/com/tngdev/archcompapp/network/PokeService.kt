package com.tngdev.archcompapp.network

import com.tngdev.archcompapp.model.ListResponse
import com.tngdev.archcompapp.model.PkmItem
import com.tngdev.archcompapp.model.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeService {
    @GET("pokemon")
    fun listPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<ListResponse<PkmItem>>

    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id: Int): Call<Pokemon>

    @GET("pokemon/{name}")
    fun getPokemon(@Path("name") name: String): Call<Pokemon>
}