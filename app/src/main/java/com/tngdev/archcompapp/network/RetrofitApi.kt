package com.tngdev.archcompapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {

    private var retrofit : Retrofit
    var pokeService: PokeService
        private set


    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        pokeService = retrofit.create(PokeService::class.java)
    }





//    companion object {
//
//        private lateinit var sInstance : RetrofitApi;
//
//        fun getInstance() : RetrofitApi {
//            if (!::sInstance.isInitialized) {
//                sInstance = RetrofitApi();
//            }
//
//            return sInstance;
//        }
//    }
}