package com.example.cleanarchitectureexample.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions


fun Fragment.navigate(
    @IdRes fragmentResId: Int,
    @IdRes popUpTo: Int? = null,
    inclusive: Boolean = false
) {
    val navOptions = navOptions {
        popUpTo?.let {
            popUpTo(it) {
                this.inclusive = inclusive
            }
        }
    }
    findNavController().navigate(
        resId = fragmentResId,
        args = null,
        navOptions = navOptions
    )
}