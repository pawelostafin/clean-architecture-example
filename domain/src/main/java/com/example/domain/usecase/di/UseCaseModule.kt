package com.example.domain.usecase.di

import com.example.domain.usecase.LoginUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory {
        LoginUseCase(
            authRepository = get()
        )
    }

}