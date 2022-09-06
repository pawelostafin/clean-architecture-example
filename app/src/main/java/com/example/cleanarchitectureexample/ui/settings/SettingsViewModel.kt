package com.example.cleanarchitectureexample.ui.settings

import com.example.cleanarchitectureexample.ui.base.ComposeBaseViewModel
import com.example.cleanarchitectureexample.ui.base.viewModelObserveFlow
import com.example.domain.model.DarkThemeMode
import com.example.domain.usecase.ObserveDarkThemeModeUseCase
import com.example.domain.usecase.SetDarkThemeModeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel(
    private val observeDarkThemeModeUseCase: ObserveDarkThemeModeUseCase,
    private val setDarkThemeModeUseCase: SetDarkThemeModeUseCase
) : ComposeBaseViewModel<SettingsViewModel.Navigation>() {

    private val _darkThemeMode = MutableStateFlow(getInitialDarkThemeMode())
    val darkThemeMode = _darkThemeMode.asStateFlow()

    private val _darkThemeModeDropdownVisibility = MutableStateFlow(false)
    val darkThemeModeDropdownVisibility = _darkThemeModeDropdownVisibility.asStateFlow()

    init {
        viewModelObserveFlow(
            flowProvider =
            { observeDarkThemeModeUseCase.execute() },
            onEach =
            { _darkThemeMode.value = it }
        )
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

    private fun getInitialDarkThemeMode(): DarkThemeMode {
        return observeDarkThemeModeUseCase.execute().value
    }

    fun backButtonClicked() {
        _navigation.trySend(Navigation.Back)
    }

    override fun systemBackButtonClicked() {
        _navigation.trySend(Navigation.Back)
    }

    sealed class Navigation {
        object Back : Navigation()
    }


}