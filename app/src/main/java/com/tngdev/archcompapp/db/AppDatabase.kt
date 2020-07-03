package com.tngdev.archcompapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tngdev.archcompapp.db.dao.PokemonDao
import com.tngdev.archcompapp.model.Pokemon

@Database(entities = [Pokemon::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun pokemonDao(): PokemonDao
}