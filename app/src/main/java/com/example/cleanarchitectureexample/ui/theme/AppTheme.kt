package com.example.cleanarchitectureexample.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

object AppTheme {

    val colors: AppColors
        @Composable
        get() {
            val isDarkTheme = isSystemInDarkTheme()
            return remember(isDarkTheme) {
                if (isDarkTheme) nightColors() else lightColors()
            }
        }

    val styles: AppStyles = AppStyles()

}