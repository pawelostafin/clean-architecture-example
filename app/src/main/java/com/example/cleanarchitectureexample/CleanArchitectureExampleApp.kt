package com.example.cleanarchitectureexample

import android.app.Application
import com.example.cleanarchitectureexample.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class CleanArchitectureExampleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@CleanArchitectureExampleApp)
            modules(appModule)
        }
    }

}