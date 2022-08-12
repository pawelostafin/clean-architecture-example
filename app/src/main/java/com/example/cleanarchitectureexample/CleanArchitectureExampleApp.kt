package com.example.cleanarchitectureexample

import android.app.Application
import com.example.cleanarchitectureexample.di.appModule
import com.example.cleanarchitectureexample.di.viewModelModule
import com.example.data.di.dataModules
import com.example.domain.di.domainModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class CleanArchitectureExampleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@CleanArchitectureExampleApp)
            modules(appModule)
            modules(viewModelModule)
            modules(dataModules)
            modules(domainModules)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}