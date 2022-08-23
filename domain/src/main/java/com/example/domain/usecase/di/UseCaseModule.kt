package com.example.domain.usecase.di

import com.example.domain.usecase.GetMarketChartDataUseCase
import com.example.domain.usecase.GetMarketUseCase
import com.example.domain.usecase.GetUserDetailsUseCase
import com.example.domain.usecase.IsUserLoggedInUseCase
import com.example.domain.usecase.LoginUseCase
import com.example.domain.usecase.LogoutUseCase
import com.example.domain.usecase.ObserveBaseCurrencyUseCase
import com.example.domain.usecase.ObserveDarkThemeModeUseCase
import com.example.domain.usecase.ObserveMarketsUseCase
import com.example.domain.usecase.SetBaseCurrencyUseCase
import com.example.domain.usecase.SetDarkThemeModeUseCase
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

    factory {
        ObserveDarkThemeModeUseCase(
            settingsRepository = get()
        )
    }

    factory {
        SetDarkThemeModeUseCase(
            settingsRepository = get()
        )
    }

    factory {
        ObserveMarketsUseCase(
            marketRepository = get()
        )
    }

    factory {
        GetMarketChartDataUseCase(
            marketRepository = get()
        )
    }

    factory {
        GetMarketUseCase(
            marketRepository = get()
        )
    }

    factory {
        ObserveBaseCurrencyUseCase(
            baseCurrencyRepository = get()
        )
    }

    factory {
        SetBaseCurrencyUseCase(
            baseCurrencyRepository = get()
        )
    }

}