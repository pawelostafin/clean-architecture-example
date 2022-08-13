package com.example.cleanarchitectureexample.ui.splash

import com.example.cleanarchitectureexample.ui.base.BaseViewModel
import com.example.cleanarchitectureexample.ui.base.EventChannel
import com.example.cleanarchitectureexample.ui.base.asFlow
import com.example.cleanarchitectureexample.ui.base.viewModelLaunch
import com.example.domain.usecase.IsUserLoggedInUseCase

class SplashViewModel(
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
) : BaseViewModel() {

    private val _navigation = EventChannel<Navigation>()
    val navigation = _navigation.asFlow()

    override fun initialize() {
        super.initialize()

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

    fun backButtonClicked() {
        _navigation.trySend(Navigation.Back)
    }

    sealed class Navigation {
        object Back : Navigation()
        object Login : Navigation()
        object Dashboard : Navigation()
    }

}