package com.example.cleanarchitectureexample.di

import com.example.cleanarchitectureexample.ui.dashboard.DashboardViewModel
import com.example.cleanarchitectureexample.ui.login.LoginViewModel
import com.example.cleanarchitectureexample.ui.main.MainViewModel
import com.example.cleanarchitectureexample.ui.profile.ProfileViewModel
import com.example.cleanarchitectureexample.ui.settings.SettingsViewModel
import com.example.cleanarchitectureexample.ui.splash.SplashViewModel
import com.example.cleanarchitectureexample.ui.test.TestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        TestViewModel()
    }

    viewModel {
        MainViewModel()
    }

    viewModel {
        LoginViewModel(
            loginUseCase = get()
        )
    }

    viewModel {
        DashboardViewModel(
            getUserDetailsUseCase = get()
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

}