package com.dominio.pokedex.main

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class PokeApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}