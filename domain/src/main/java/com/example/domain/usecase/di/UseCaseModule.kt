package com.example.domain.usecase.di

import com.example.domain.usecase.GetUserDetailsUseCase
import com.example.domain.usecase.IsUserLoggedInUseCase
import com.example.domain.usecase.LoginUseCase
import com.example.domain.usecase.LogoutUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory {
        LoginUseCase(
            authRepository = get()
        )
    }

    factory {
        GetUserDetailsUseCase(
            userDetailsRepository = get()
        )
    }

    factory {
        LogoutUseCase(
            authRepository = get()
        )
    }

    factory {
        IsUserLoggedInUseCase(
            authRepository = get()
        )
    }

}