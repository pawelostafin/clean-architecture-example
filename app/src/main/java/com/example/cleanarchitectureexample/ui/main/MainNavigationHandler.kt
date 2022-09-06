package com.example.cleanarchitectureexample.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cleanarchitectureexample.ui.dashboard.DashboardScreen
import com.example.cleanarchitectureexample.ui.details.DetailsScreen
import com.example.cleanarchitectureexample.ui.login.LoginScreen
import com.example.cleanarchitectureexample.ui.profile.ProfileScreen
import com.example.cleanarchitectureexample.ui.settings.SettingsScreen
import com.example.cleanarchitectureexample.ui.splash.SplashScreen
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MainNavigationHandler() {
    val mainNavController = rememberNavController()
    val mainNavigationHelper = remember(mainNavController) { MainNavigationHelper(mainNavController) }
    NavHost(
        navController = mainNavController,
        startDestination = MainRoutes.Splash.graphRoute,
        route = MainRoutes.Root.graphRoute
    ) {
        composable(MainRoutes.Splash.graphRoute) {
            SplashScreen(
                viewModel = getViewModel(),
                navigate = mainNavigationHelper::handleSplashNavigation
            )
        }
        composable(MainRoutes.Login.graphRoute) {
            LoginScreen(
                viewModel = getViewModel(),
                navigate = mainNavigationHelper::handleLoginNavigation
            )
        }
        composable(MainRoutes.Dashboard.graphRoute) {
            DashboardScreen(
                viewModel = getViewModel(),
                navigate = mainNavigationHelper::handleDashboardNavigation
            )
        }
        composable(MainRoutes.Settings.graphRoute) {
            SettingsScreen(
                viewModel = getViewModel(),
                navigate = mainNavigationHelper::handleSettingsNavigation
            )
        }
        composable(MainRoutes.Details.graphRoute) {
            val currencyId = it.arguments!!.getString(MainRoutes.Details.CURRENCY_ID)
            DetailsScreen(
                viewModel = getViewModel(parameters = { parametersOf(currencyId) }),
                navigate = mainNavigationHelper::handleDetailsNavigation
            )
        }
        composable(MainRoutes.Profile.graphRoute) {
            ProfileScreen(
                viewModel = getViewModel(),
                navigate = mainNavigationHelper::handleProfileNavigation
            )
        }
    }
}