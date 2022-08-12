package com.example.data.repository.di

import com.example.data.repository.AuthRepositoryImpl
import com.example.domain.repository.AuthRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory<AuthRepository> {
        AuthRepositoryImpl(
            authService = get()
        )
    }

}