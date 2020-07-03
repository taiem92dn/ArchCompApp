package com.tngdev.archcompapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tngdev.archcompapp.model.Pokemon

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")
    fun getAll(): LiveData<List<Pokemon>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemon: Pokemon)

    @Insert
    fun insertAll(pokemons: List<Pokemon>)

    @Delete
    fun delete(pokemon: Pokemon)
}