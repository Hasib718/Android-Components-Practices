package com.hasib.marsrealestate

import android.app.Application
import timber.log.Timber

class AppClass : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}