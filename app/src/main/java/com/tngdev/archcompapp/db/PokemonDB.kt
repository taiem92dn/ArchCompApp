package com.tngdev.archcompapp.db

import androidx.room.Room
import com.tngdev.archcompapp.App

object PokemonDB {
    val appDatabase : AppDatabase = Room.databaseBuilder(App.instance, AppDatabase::class.java, "PokemonDB").build()

}