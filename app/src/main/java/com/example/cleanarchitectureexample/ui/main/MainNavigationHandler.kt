package com.example.cleanarchitectureexample.ui.main

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cleanarchitectureexample.ui.dashboard.DashboardNavigator
import com.example.cleanarchitectureexample.ui.dashboard.DashboardScreen
import com.example.cleanarchitectureexample.ui.details.DetailsNavigator
import com.example.cleanarchitectureexample.ui.details.DetailsScreen
import com.example.cleanarchitectureexample.ui.login.LoginNavigator
import com.example.cleanarchitectureexample.ui.login.LoginScreen
import com.example.cleanarchitectureexample.ui.profile.ProfileNavigator
import com.example.cleanarchitectureexample.ui.profile.ProfileScreen
import com.example.cleanarchitectureexample.ui.settings.SettingsNavigator
import com.example.cleanarchitectureexample.ui.settings.SettingsScreen
import com.example.cleanarchitectureexample.ui.splash.SplashNavigator
import com.example.cleanarchitectureexample.ui.splash.SplashScreen
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MainNavigationHandler() {
    val navController = rememberNavControllerWithDisabledOnBackPressed()
    val mainNavControllerWithCloseAction = NavControllerWithCloseAction(
        navController = navController,
        onCloseNavHolderAction = {
            (navController.context as ComponentActivity).moveTaskToBack(true)
        }
    )
    NavHost(
        navController = navController,
        startDestination = MainRoutes.Splash.graphRoute,
        route = MainRoutes.Root.graphRoute
    ) {
        composable(MainRoutes.Splash.graphRoute) {
            val navigator = remember(mainNavControllerWithCloseAction) { SplashNavigator(mainNavControllerWithCloseAction) }
            SplashScreen(
                viewModel = getViewModel(),
                navigate = navigator::handleNavigation
            )
        }
        composable(MainRoutes.Login.graphRoute) {
            val navigator = remember(mainNavControllerWithCloseAction) { LoginNavigator(mainNavControllerWithCloseAction) }
            LoginScreen(
                viewModel = getViewModel(),
                navigate = navigator::handleNavigation
            )
        }
        composable(MainRoutes.Dashboard.graphRoute) {
            val navigator = remember(mainNavControllerWithCloseAction) { DashboardNavigator(mainNavControllerWithCloseAction) }
            DashboardScreen(
                viewModel = getViewModel(),
                navigate = navigator::handleNavigation
            )
        }
        composable(MainRoutes.Settings.graphRoute) {
            val navigator = remember(mainNavControllerWithCloseAction) { SettingsNavigator(mainNavControllerWithCloseAction) }
            SettingsScreen(
                viewModel = getViewModel(),
                navigate = navigator::handleNavigation
            )
        }
        composable(MainRoutes.Details.graphRoute) {
            val navigator = remember(mainNavControllerWithCloseAction) { DetailsNavigator(mainNavControllerWithCloseAction) }
            val currencyId = it.arguments!!.getString(MainRoutes.Details.CURRENCY_ID)
            DetailsScreen(
                viewModel = getViewModel(parameters = { parametersOf(currencyId) }),
                navigate = navigator::handleNavigation
            )
        }
        composable(MainRoutes.Profile.graphRoute) {
            val navigator = remember(mainNavControllerWithCloseAction) { ProfileNavigator(mainNavControllerWithCloseAction) }
            ProfileScreen(
                viewModel = getViewModel(),
                navigate = navigator::handleNavigation
            )
        }
    }
}

@Composable
fun rememberNavControllerWithDisabledOnBackPressed(): NavHostController {
    val navController = rememberNavController()
    key(navController) { navController.enableOnBackPressed(false) }
    return navController
}

data class NavControllerWithCloseAction(
    val navController: NavHostController,
    val onCloseNavHolderAction: () -> Unit
)
