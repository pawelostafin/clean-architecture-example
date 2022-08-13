package com.example.cleanarchitectureexample.di

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import org.koin.dsl.module

val appModule = module {

    single<SharedPreferences> {
        PreferenceManager.getDefaultSharedPreferences(get())
    }

}