package com.example.data.repository.di

import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.UserDetailsRepositoryImpl
import com.example.domain.repository.AuthRepository
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

}