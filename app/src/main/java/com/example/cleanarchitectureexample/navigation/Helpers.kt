package com.example.cleanarchitectureexample.navigation

import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.navOptions

fun NavHostController.supportNavigate(
    route: String,
    popUpToRoute: String? = null,
    inclusive: Boolean = false,
    // currently no-op (waiting for animation support in compose-navigation)
    transition: NavTransition = NavTransition.RIGHT,
) {
    val navOptions = navOptions {
//        when (transition) {
//            NavTransition.RIGHT -> applyTransitionRight()
//            NavTransition.FADE -> applyTransitionFade()
//            NavTransition.BOTTOM -> applyTransitionBottom()
//        }
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

fun NavHostController.navigateBackOrCloseNavHolder(closeNavHolderAction: () -> Unit) {
    val destinationCountOnBackStack = backQueue.count { entry ->
        entry.destination !is NavGraph
    }

    if (destinationCountOnBackStack == 1) {
        closeNavHolderAction.invoke()
    } else {
        popBackStack()
    }
}

enum class NavTransition {
    RIGHT,
    FADE,
    BOTTOM
}