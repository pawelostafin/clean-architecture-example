package com.example.cleanarchitectureexample.di

import com.example.cleanarchitectureexample.ui.dashboard.DashboardViewModel
import com.example.cleanarchitectureexample.ui.details.DetailsViewModel
import com.example.cleanarchitectureexample.ui.login.LoginViewModel
import com.example.cleanarchitectureexample.ui.profile.ProfileViewModel
import com.example.cleanarchitectureexample.ui.settings.SettingsViewModel
import com.example.cleanarchitectureexample.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        LoginViewModel(
            loginUseCase = get()
        )
    }

    viewModel {
        DashboardViewModel(
            getUserDetailsUseCase = get(),
            observeMarketsUseCase = get(),
            observeBaseCurrencyUseCase = get(),
            setBaseCurrencyUseCase = get(),
            dispatchersProvider = get()
        )
    }

    viewModel {
        ProfileViewModel(
            logoutUseCase = get(),
            getUserDetailsUseCase = get()
        )
    }

    viewModel {
        SplashViewModel(
            isUserLoggedInUseCase = get()
        )
    }

    viewModel {
        SettingsViewModel(
            observeDarkThemeModeUseCase = get(),
            setDarkThemeModeUseCase = get()
        )
    }

    viewModel { (currencyId: String) ->
        DetailsViewModel(
            currencyId = currencyId,
            getMarketChartDataUseCase = get(),
            getMarketUseCase = get(),
            getBaseCurrencyCodeUseCase = get()
        )
    }

}