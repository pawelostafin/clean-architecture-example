package com.example.cleanarchitectureexample.ui.theme

import androidx.compose.ui.graphics.Color

// TODO: colors can be reworked to separate mutableStates like in compose internals
class AppColors(
    val primary: Color,
    val ripple: Color,
    val textColorPrimary: Color,
    val textColorSecondary: Color,
    val backgroundPrimary: Color,
    val backgroundSecondary: Color,
)

// TODO: change to light colors palette
fun lightColors() = AppColors(
    primary = Color(0xFFFFCF26),
    ripple = Color(0xFFDADADA),
    textColorPrimary = Color(0xFFDADADA),
    textColorSecondary = Color(0xFF636363),
    backgroundPrimary = Color(0xFF131313),
    backgroundSecondary = Color(0xFF212121),
)

fun nightColors(): AppColors {
    return AppColors(
        primary = Color(0xFFFFCF26),
        ripple = Color(0xFFDADADA),
        textColorPrimary = Color(0xFFDADADA),
        textColorSecondary = Color(0xFF636363),
        backgroundPrimary = Color(0xFF131313),
        backgroundSecondary = Color(0xFF212121),
    )
}