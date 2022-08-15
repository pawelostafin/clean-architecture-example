package com.example.domain.repository

import com.example.domain.model.DarkThemeMode
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {

    fun observeDarkThemeMode(): StateFlow<DarkThemeMode>

    fun setDarkThemeMode(mode: DarkThemeMode)

}