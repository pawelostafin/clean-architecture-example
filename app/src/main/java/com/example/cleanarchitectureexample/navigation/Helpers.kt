package com.example.cleanarchitectureexample.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.cleanarchitectureexample.R


fun Fragment.navigate(
    route: String,
    popUpToRoute: String? = null,
    inclusive: Boolean = false,
    transition: NavTransition = NavTransition.RIGHT,
) {
    val navOptions = navOptions {
        when (transition) {
            NavTransition.RIGHT -> applyTransitionRight()
            NavTransition.FADE -> applyTransitionFade()
            NavTransition.BOTTOM -> applyTransitionBottom()
        }
        popUpToRoute?.let {
            popUpTo(
                route = it,
                popUpToBuilder = {
                    this.inclusive = inclusive
                }
            )
        }
    }
    findNavController().navigate(
        route = route,
        navOptions = navOptions
    )
}

private fun NavOptionsBuilder.applyTransitionRight() =
    anim {
        enter = R.anim.slide_in_right
        exit = R.anim.slide_out_left
        popEnter = R.anim.slide_in_left
        popExit = R.anim.slide_out_right
    }

private fun NavOptionsBuilder.applyTransitionBottom() =
    anim {
        enter = R.anim.slide_in_bottom
        exit = R.anim.partial_fade_out
        popEnter = R.anim.partial_fade_in
        popExit = R.anim.slide_out_bottom
    }

private fun NavOptionsBuilder.applyTransitionFade() =
    anim {
        enter = R.anim.fade_in
        exit = R.anim.do_nothing
        popEnter = R.anim.do_nothing
        popExit = R.anim.fade_out
    }

enum class NavTransition {
    RIGHT,
    FADE,
    BOTTOM
}

