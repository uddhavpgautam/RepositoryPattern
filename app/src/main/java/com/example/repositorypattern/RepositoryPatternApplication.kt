package com.example.repositorypattern

import android.app.Application
import com.facebook.shimmer.BuildConfig
import timber.log.Timber


class RepositoryPatternApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}