package com.example.cleanarchitectureexample.ui.utli

import androidx.compose.ui.graphics.Color

fun Color.withAlpha(alpha: Float): Color {
    return copy(alpha = alpha)
}