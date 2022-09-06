package com.example.cleanarchitectureexample.ui.splash

import com.example.cleanarchitectureexample.ui.base.ComposeBaseViewModel
import com.example.cleanarchitectureexample.ui.base.viewModelLaunch
import com.example.domain.usecase.IsUserLoggedInUseCase

class SplashViewModel(
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
) : ComposeBaseViewModel<SplashViewModel.Navigation>() {

    init {
        viewModelLaunch {
            val isUserLoggedIn = isUserLoggedInUseCase.execute()
            val destination = if (isUserLoggedIn) {
                Navigation.Dashboard
            } else {
                Navigation.Login
            }
            _navigation.trySend(destination)
        }
    }

    override fun systemBackButtonClicked() {
        // do nothing
    }

    sealed class Navigation {
        object Login : Navigation()
        object Dashboard : Navigation()
    }

}