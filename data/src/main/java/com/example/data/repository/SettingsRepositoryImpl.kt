package com.example.data.repository

import android.content.SharedPreferences
import com.example.domain.model.DarkThemeMode
import com.example.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : SettingsRepository {

    private val darkThemeMode by lazy {
        val rawValue = sharedPreferences.getString(KEY_DARK_THEME_MODE, null)
        val mode = rawValue
            ?.let { DarkThemeMode.fromRawValue(rawValue) }
            ?: DEFAULT_DARK_THEME_MODE
        MutableStateFlow(mode)
    }

    override fun observeDarkThemeMode(): StateFlow<DarkThemeMode> {
        return darkThemeMode
    }

    override fun setDarkThemeMode(mode: DarkThemeMode) {
        sharedPreferences.edit().putString(KEY_DARK_THEME_MODE, mode.toRawValue()).apply()
        darkThemeMode.value = mode
    }

    companion object {
        private const val KEY_DARK_THEME_MODE = "KEY_DARK_THEME_MODE"
        private val DEFAULT_DARK_THEME_MODE = DarkThemeMode.ENABLED
    }

}