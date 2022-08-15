package com.example.cleanarchitectureexample.ui.settings

import com.example.cleanarchitectureexample.ui.base.BaseViewModel
import com.example.cleanarchitectureexample.ui.base.EventChannel
import com.example.cleanarchitectureexample.ui.base.asFlow
import com.example.cleanarchitectureexample.ui.base.viewModelObserveFlow
import com.example.domain.model.DarkThemeMode
import com.example.domain.usecase.ObserveDarkThemeModeUseCase
import com.example.domain.usecase.SetDarkThemeModeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel(
    private val observeDarkThemeModeUseCase: ObserveDarkThemeModeUseCase,
    private val setDarkThemeModeUseCase: SetDarkThemeModeUseCase
) : BaseViewModel() {

    private val _navigation = EventChannel<Navigation>()
    val navigation = _navigation.asFlow()

    private val _darkThemeMode = MutableStateFlow(getInitialDarkThemeMode())
    val darkThemeMode = _darkThemeMode.asStateFlow()

    private val _darkThemeModeDropdownVisibility = MutableStateFlow(false)
    val darkThemeModeDropdownVisibility = _darkThemeModeDropdownVisibility.asStateFlow()

    fun backButtonClicked() {
        _navigation.trySend(Navigation.Back)
    }

    init {
        viewModelObserveFlow(
            flowProvider = { observeDarkThemeModeUseCase.execute() },
            onEach = { _darkThemeMode.value = it }
        )
    }

    private fun getInitialDarkThemeMode(): DarkThemeMode {
        return observeDarkThemeModeUseCase.execute().value
    }

    fun darkThemeModeChangeRequested(newValue: DarkThemeMode) {
        setDarkThemeModeUseCase.execute(mode = newValue)
        _darkThemeModeDropdownVisibility.value = false
    }

    fun darkThemeDropdownDismissRequested() {
        _darkThemeModeDropdownVisibility.value = false
    }

    fun darkThemeItemClicked() {
        _darkThemeModeDropdownVisibility.value = true
    }

    sealed class Navigation {
        object Back : Navigation()
    }

}