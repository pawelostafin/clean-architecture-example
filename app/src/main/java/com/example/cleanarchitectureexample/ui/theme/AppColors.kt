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

    val dropdownBackground: Color,
    val dialogButton: Color,
    val profileImageBorder: Color,

    val chartGreen: Color,
    val chartRed: Color
)

// TODO: change to light colors palette
fun lightColors(): AppColors {
    val backgroundPrimary = Color(0xFFF2F2F2)
    val textColorPrimary = Color(0xFF212121)
    val textColorSecondary = Color(0xFFB3B3B3)
    return AppColors(
        primary = Color(0xFFFFCF26),
        ripple = Color.Unspecified,
        textColorPrimary = textColorPrimary,
        textColorSecondary = textColorSecondary,
        backgroundPrimary = backgroundPrimary,
        backgroundSecondary = Color(0xFFE8E8E8),
        dropdownBackground = backgroundPrimary,
        dialogButton = textColorPrimary,
        profileImageBorder = Color(0xFF838383),
        chartGreen = Color.Green,
        chartRed = Color(red = 255, green = 69, blue = 58)
    )
}

fun darkColors(): AppColors {
    val primary = Color(0xFFFFCF26)
    val backgroundSecondary = Color(0xFF212121)
    val textColorPrimary = Color(0xFFDADADA)
    val textColorSecondary = Color(0xFF636363)
    return AppColors(
        primary = primary,
        ripple = Color(0xFFDADADA),
        textColorPrimary = textColorPrimary,
        textColorSecondary = textColorSecondary,
        backgroundPrimary = Color(0xFF131313),
        backgroundSecondary = backgroundSecondary,
        dropdownBackground = backgroundSecondary,
        dialogButton = primary,
        profileImageBorder = textColorPrimary,
        chartGreen = Color.Green,
        chartRed = Color(red = 255, green = 69, blue = 58)
    )
}