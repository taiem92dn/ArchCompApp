package com.tngdev.archcompapp.di.module

import com.tngdev.archcompapp.db.dao.PokemonDao
import com.tngdev.archcompapp.network.PokeService
import com.tngdev.archcompapp.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

}