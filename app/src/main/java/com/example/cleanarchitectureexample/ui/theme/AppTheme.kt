package com.example.cleanarchitectureexample.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.domain.model.DarkThemeMode
import kotlinx.coroutines.flow.StateFlow

object AppTheme {

    lateinit var darkThemeModeFlow: StateFlow<DarkThemeMode>

    val colors: AppColors
        @Composable
        get() {
            val darkThemeMode by darkThemeModeFlow.collectAsState()
            val isSystemDarkTheme = isSystemInDarkTheme()
            return remember(isSystemDarkTheme, darkThemeMode) {
                when (darkThemeMode) {
                    DarkThemeMode.ENABLED -> darkColors()
                    DarkThemeMode.DISABLED -> lightColors()
                    DarkThemeMode.AUTO -> if (isSystemDarkTheme) darkColors() else lightColors()
                }
            }
        }

    val styles: AppStyles = AppStyles()

}