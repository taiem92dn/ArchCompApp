package com.tngdev.archcompapp.di.module

import android.app.Application
import androidx.room.Room
import com.tngdev.archcompapp.db.AppDatabase
import com.tngdev.archcompapp.db.dao.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDB(app: Application) : AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "PokemonDB").build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(appDatabase: AppDatabase) : PokemonDao {
        return appDatabase.pokemonDao()
    }
}