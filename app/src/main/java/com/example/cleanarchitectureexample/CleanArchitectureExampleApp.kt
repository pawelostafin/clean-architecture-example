package com.example.cleanarchitectureexample

import android.app.Application
import com.example.cleanarchitectureexample.di.appModule
import com.example.cleanarchitectureexample.di.viewModelModule
import com.example.cleanarchitectureexample.ui.theme.AppTheme
import com.example.data.di.dataModules
import com.example.domain.di.domainModules
import com.example.domain.repository.SettingsRepository
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class CleanArchitectureExampleApp : Application() {

    private val settingsRepository: SettingsRepository by inject()

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKoin()
        initAppTheme()
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

    private fun initAppTheme() {
        AppTheme.darkThemeModeFlow = settingsRepository.observeDarkThemeMode()
    }

}