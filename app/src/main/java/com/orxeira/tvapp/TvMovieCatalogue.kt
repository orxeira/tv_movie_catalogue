package com.orxeira.tvapp

import android.app.Application

class TvMovieCatalogue : Application() {
    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}