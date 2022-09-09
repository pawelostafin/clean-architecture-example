package com.example.cleanarchitectureexample.ui.profile

import com.example.cleanarchitectureexample.navigation.supportNavigate
import com.example.cleanarchitectureexample.ui.main.MainRoutes
import com.example.cleanarchitectureexample.ui.main.NavControllerWithCloseAction
import com.example.cleanarchitectureexample.ui.splash.BaseNavigator

class ProfileNavigator(navControllerWithCloseAction: NavControllerWithCloseAction) :
    BaseNavigator<ProfileViewModel.Navigation>(navControllerWithCloseAction) {

    override fun handleNavigation(navigation: ProfileViewModel.Navigation) {
        when (navigation) {
            ProfileViewModel.Navigation.Back -> navigateBackOrCloseNavHolder()
            ProfileViewModel.Navigation.Login -> navController.supportNavigate(
                route = MainRoutes.Login.navigationRoute,
                popUpToRoute = MainRoutes.Root.graphRoute
            )
            ProfileViewModel.Navigation.Settings -> navController.supportNavigate(
                route = MainRoutes.Settings.navigationRoute
            )
        }
    }

}