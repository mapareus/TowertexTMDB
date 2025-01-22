package com.towertex.tmdb

import android.app.Application
import com.towertex.tmdb.di.repositoryModule
import com.towertex.tmdb.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level

class TMDBApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidLogger(
                if (BuildConfig.DEBUG) { // Logs detailed information about Koin dependency resolutions
                    Level.DEBUG
                } else {
                    Level.NONE
                }
            )
            androidContext(this@TMDBApplication)
            modules(listOf(repositoryModule, viewModelModule))
        }
    }
}