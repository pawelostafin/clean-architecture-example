package com.example.cleanarchitectureexample.ui.login

import com.example.cleanarchitectureexample.navigation.supportNavigate
import com.example.cleanarchitectureexample.ui.main.MainRoutes
import com.example.cleanarchitectureexample.ui.main.NavControllerWithCloseAction
import com.example.cleanarchitectureexample.ui.splash.BaseNavigator

class LoginNavigator(navControllerWithCloseAction: NavControllerWithCloseAction) :
    BaseNavigator<LoginViewModel.Navigation>(navControllerWithCloseAction) {

    override fun handleNavigation(navigation: LoginViewModel.Navigation) {
        when (navigation) {
            LoginViewModel.Navigation.Back -> navigateBackOrCloseNavHolder()
            LoginViewModel.Navigation.Dashboard -> navController.supportNavigate(
                route = MainRoutes.Dashboard.navigationRoute,
                popUpToRoute = MainRoutes.Root.graphRoute
            )
        }
    }

}