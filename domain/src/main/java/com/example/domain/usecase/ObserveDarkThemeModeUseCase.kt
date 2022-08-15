package com.example.domain.usecase

import com.example.domain.model.DarkThemeMode
import com.example.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.StateFlow

class ObserveDarkThemeModeUseCase(
    private val settingsRepository: SettingsRepository
) {

    fun execute(): StateFlow<DarkThemeMode>{
        return settingsRepository.observeDarkThemeMode()
    }

}