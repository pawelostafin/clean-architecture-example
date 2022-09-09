package com.example.cleanarchitectureexample.ui.dashboard

import com.example.cleanarchitectureexample.navigation.supportNavigate
import com.example.cleanarchitectureexample.ui.main.MainRoutes
import com.example.cleanarchitectureexample.ui.main.NavControllerWithCloseAction
import com.example.cleanarchitectureexample.ui.splash.BaseNavigator

class DashboardNavigator(navControllerWithCloseAction: NavControllerWithCloseAction) :
    BaseNavigator<DashboardViewModel.Navigation>(navControllerWithCloseAction) {

    override fun handleNavigation(navigation: DashboardViewModel.Navigation) {
        when (navigation) {
            DashboardViewModel.Navigation.Back -> navigateBackOrCloseNavHolder()
            is DashboardViewModel.Navigation.Details -> navController.supportNavigate(
                route = MainRoutes.Details.buildFullNavigationRoute(currencyId = navigation.currencyId)
            )
            DashboardViewModel.Navigation.Profile -> navController.supportNavigate(
                route = MainRoutes.Profile.navigationRoute
            )
        }
    }

}