package com.example.cleanarchitectureexample.ui.dashboard

import com.example.cleanarchitectureexample.ui.base.BaseViewModel
import com.example.cleanarchitectureexample.ui.base.EventChannel
import com.example.cleanarchitectureexample.ui.base.asFlow

class DashboardViewModel : BaseViewModel() {

    private val _navigation = EventChannel<Navigation>()
    val navigation = _navigation.asFlow()

    fun backButtonClicked() {
        _navigation.trySend(Navigation.Back)
    }

    sealed class Navigation {
        object Back : Navigation()
        object Profile : Navigation()
    }

}