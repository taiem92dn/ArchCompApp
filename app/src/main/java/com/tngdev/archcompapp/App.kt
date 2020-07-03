package com.tngdev.archcompapp

import android.app.Application
import androidx.room.Room
import com.tngdev.archcompapp.db.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        @JvmStatic
        lateinit var instance : App
    }
}