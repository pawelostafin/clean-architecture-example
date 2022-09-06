package com.example.cleanarchitectureexample.ui.main

import androidx.navigation.NavHostController
import com.example.cleanarchitectureexample.navigation.navigateBackOrClose
import com.example.cleanarchitectureexample.navigation.supportNavigate
import com.example.cleanarchitectureexample.ui.dashboard.DashboardViewModel
import com.example.cleanarchitectureexample.ui.details.DetailsViewModel
import com.example.cleanarchitectureexample.ui.login.LoginViewModel
import com.example.cleanarchitectureexample.ui.profile.ProfileViewModel
import com.example.cleanarchitectureexample.ui.settings.SettingsViewModel
import com.example.cleanarchitectureexample.ui.splash.SplashViewModel

class MainNavigationHelper(
    private val navController: NavHostController
) {

    fun handleProfileNavigation(navigation: ProfileViewModel.Navigation) {
        when (navigation) {
            ProfileViewModel.Navigation.Back -> navigateBackOrClose()
            ProfileViewModel.Navigation.Login -> navController.supportNavigate(
                route = MainRoutes.Login.navigationRoute,
                popUpToRoute = MainRoutes.Root.graphRoute
            )
            ProfileViewModel.Navigation.Settings -> navController.supportNavigate(
                route = MainRoutes.Settings.navigationRoute
            )
        }
    }

    fun handleDetailsNavigation(navigation: DetailsViewModel.Navigation) {
        when (navigation) {
            DetailsViewModel.Navigation.Back -> navigateBackOrClose()
        }
    }

    fun handleSettingsNavigation(navigation: SettingsViewModel.Navigation) {
        when (navigation) {
            SettingsViewModel.Navigation.Back -> navigateBackOrClose()
        }
    }

    fun handleDashboardNavigation(navigation: DashboardViewModel.Navigation) {
        when (navigation) {
            DashboardViewModel.Navigation.Back -> navigateBackOrClose()
            is DashboardViewModel.Navigation.Details -> navController.supportNavigate(
                route = MainRoutes.Details.buildFullNavigationRoute(currencyId = navigation.currencyId)
            )
            DashboardViewModel.Navigation.Profile -> navController.supportNavigate(
                route = MainRoutes.Profile.navigationRoute
            )
        }
    }

    fun handleLoginNavigation(navigation: LoginViewModel.Navigation) {
        when (navigation) {
            LoginViewModel.Navigation.Back -> navController.navigateBackOrClose()
            LoginViewModel.Navigation.Dashboard -> navController.supportNavigate(
                route = MainRoutes.Dashboard.navigationRoute,
                popUpToRoute = MainRoutes.Root.graphRoute
            )
        }
    }

    fun handleSplashNavigation(navigation: SplashViewModel.Navigation) {
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

    private fun navigateBackOrClose() {
        navController.navigateBackOrClose()
    }

}