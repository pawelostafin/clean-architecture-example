package com.example.data.repository.di

import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.BaseCurrencyRepositoryImpl
import com.example.data.repository.MarketRepositoryImpl
import com.example.data.repository.SettingsRepositoryImpl
import com.example.data.repository.UserDetailsRepositoryImpl
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.BaseCurrencyRepository
import com.example.domain.repository.MarketRepository
import com.example.domain.repository.SettingsRepository
import com.example.domain.repository.UserDetailsRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory<AuthRepository> {
        AuthRepositoryImpl(
            authService = get(),
            sessionDataHolder = get()
        )
    }

    factory<UserDetailsRepository> {
        UserDetailsRepositoryImpl(
            userDetailsService = get(),
            sessionDataHolder = get()
        )
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(
            sharedPreferences = get()
        )
    }


    single<MarketRepository> {
        MarketRepositoryImpl(
            marketService = get(),
            dispatchersProvider = get()
        )
    }

    single<BaseCurrencyRepository> {
        BaseCurrencyRepositoryImpl(
            sharedPreferences = get()
        )
    }

}