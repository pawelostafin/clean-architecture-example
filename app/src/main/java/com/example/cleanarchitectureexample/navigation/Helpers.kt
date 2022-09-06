package com.example.cleanarchitectureexample.navigation

import androidx.activity.ComponentActivity
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import com.example.cleanarchitectureexample.R
import timber.log.Timber


fun NavHostController.supportNavigate(
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
    navigate(
        route = route,
        navOptions = navOptions
    )
}

fun NavHostController.navigateBackOrClose() {
    Timber.d("ELOELO clicked!")
    val poppedBackStack = popBackStack()
    if (!poppedBackStack) {
        (context as? ComponentActivity)?.finish()
    }
    Timber.d("ELOELO $poppedBackStack")
}

//TODO currently not working waits for compose navigation animations
private fun NavOptionsBuilder.applyTransitionRight() =
    anim {
        enter = R.anim.slide_in_right
        exit = R.anim.slide_out_left
        popEnter = R.anim.slide_in_left
        popExit = R.anim.slide_out_right
    }

//TODO currently not working waits for compose navigation animations
private fun NavOptionsBuilder.applyTransitionBottom() =
    anim {
        enter = R.anim.slide_in_bottom
        exit = R.anim.partial_fade_out
        popEnter = R.anim.partial_fade_in
        popExit = R.anim.slide_out_bottom
    }

//TODO currently not working waits for compose navigation animations
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

