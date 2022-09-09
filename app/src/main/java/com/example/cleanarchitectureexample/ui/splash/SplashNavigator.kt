package com.example.cleanarchitectureexample.ui.splash

import com.example.cleanarchitectureexample.navigation.navigateBackOrCloseNavHolder
import com.example.cleanarchitectureexample.navigation.supportNavigate
import com.example.cleanarchitectureexample.ui.main.MainRoutes
import com.example.cleanarchitectureexample.ui.main.NavControllerWithCloseAction

class SplashNavigator(navControllerWithCloseAction: NavControllerWithCloseAction) :
    BaseNavigator<SplashViewModel.Navigation>(navControllerWithCloseAction) {

    override fun handleNavigation(navigation: SplashViewModel.Navigation) {
        when (navigation) {
            SplashViewModel.Navigation.Dashboard -> navController.supportNavigate(
                route = MainRoutes.Dashboard.navigationRoute,
                popUpToRoute = MainRoutes.Root.graphRoute
            )
            SplashViewModel.Navigation.Login -> navController.supportNavigate(
                route = MainRoutes.Login.navigationRoute,
                popUpToRoute = MainRoutes.Root.graphRoute
            )
        }
    }

}

abstract class BaseNavigator<T>(
    private val navControllerWithCloseAction: NavControllerWithCloseAction,
) {

    protected val navController = navControllerWithCloseAction.navController

    abstract fun handleNavigation(navigation: T)

    protected fun navigateBackOrCloseNavHolder() {
        navController.navigateBackOrCloseNavHolder(
            closeNavHolderAction = navControllerWithCloseAction.onCloseNavHolderAction
        )
    }

    protected fun closeNavHolder() {
        navControllerWithCloseAction.onCloseNavHolderAction.invoke()
    }

}

