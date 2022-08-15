package com.example.domain.usecase

import com.example.domain.model.DarkThemeMode
import com.example.domain.repository.SettingsRepository

class SetDarkThemeModeUseCase(
    private val settingsRepository: SettingsRepository
) {

    fun execute(mode: DarkThemeMode) {
        settingsRepository.setDarkThemeMode(mode)
    }

}