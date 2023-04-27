package com.example.repositorypattern

import android.app.Application
import android.os.StrictMode
import android.util.Log
import com.example.repositorypattern.viewmodelsample.repository.data.MenuDb
import com.example.repositorypattern.viewmodelsample.repository.data.MenuItem
import com.facebook.shimmer.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber


@HiltAndroidApp
class RepositoryPatternApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        enableStrictMode()

        applicationScope.launch {
            //Room database
            val menuDb = MenuDb.getDatabase(this@RepositoryPatternApplication, applicationScope, "navigation/dynamicMenu.json")

            val menuItems: List<MenuItem> = menuDb.menuItemDao().getMenuItems()

            Log.d("Application", menuItems.toString())
        }

    }

    private fun enableStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
        }
    }
}

