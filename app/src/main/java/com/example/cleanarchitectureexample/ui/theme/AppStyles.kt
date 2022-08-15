package com.example.cleanarchitectureexample.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

class AppStyles {

    val editTextErrorStyle
        @Composable
        get() = TextStyle(
            color = Color.Red
        )

    val editTextTextStyle
        @Composable
        get() = TextStyle(
            color = AppTheme.colors.textColorPrimary
        )

    val editTextHintStyle
        @Composable
        get() = TextStyle(
            color = AppTheme.colors.textColorSecondary
        )

}