package com.example.cleanarchitectureexample.ui.main

import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.example.cleanarchitectureexample.databinding.ActivityMainBinding
import com.example.cleanarchitectureexample.ui.base.BaseActivity
import com.example.cleanarchitectureexample.ui.dashboard.DashboardFragment
import com.example.cleanarchitectureexample.ui.details.DetailsFragment
import com.example.cleanarchitectureexample.ui.login.LoginFragment
import com.example.cleanarchitectureexample.ui.profile.ProfileFragment
import com.example.cleanarchitectureexample.ui.settings.SettingsFragment
import com.example.cleanarchitectureexample.ui.splash.SplashFragment
import com.example.cleanarchitectureexample.ui.test.TestFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(ActivityMainBinding::inflate) {

    override val viewModel: MainViewModel by viewModel()

    override fun initView() {
        super.initView()

        initNavigation()
    }

    override fun initObservers() {
        super.initObservers()
    }

    private fun initNavigation() {
        val navHostFragment = (binding.navHostContainer.getFragment() as NavHostFragment)
        val navController = navHostFragment.navController

        navController.apply {
            graph = createGraph(
                startDestination = MainGraphRoutes.Splash.graphRoute,
                route = MainGraphRoutes.Root.graphRoute
            ) {
                fragment<SplashFragment>(route = MainGraphRoutes.Splash.graphRoute)
                fragment<DashboardFragment>(route = MainGraphRoutes.Dashboard.graphRoute)
                fragment<LoginFragment>(route = MainGraphRoutes.Login.graphRoute)
                fragment<SettingsFragment>(route = MainGraphRoutes.Settings.graphRoute)
                fragment<DetailsFragment>(route = MainGraphRoutes.Details.graphRoute)
                fragment<TestFragment>(route = MainGraphRoutes.Test.graphRoute)
                fragment<ProfileFragment>(route = MainGraphRoutes.Profile.graphRoute)
            }
        }
    }

}

interface NavRoute {
    val graphRoute: String
    val navigationRoute: String
}

sealed class MainGraphRoutes : NavRoute {
    object Root : MainGraphRoutes() {
        override val graphRoute: String = "main_graph"
        override val navigationRoute: String = graphRoute
    }

    object Splash : MainGraphRoutes() {
        override val graphRoute: String = "splash"
        override val navigationRoute: String = graphRoute
    }

    object Login : MainGraphRoutes() {
        override val graphRoute: String = "login"
        override val navigationRoute: String = graphRoute
    }

    object Dashboard : MainGraphRoutes() {
        override val graphRoute: String = "dashboard"
        override val navigationRoute: String = graphRoute
    }

    object Settings : MainGraphRoutes() {
        override val graphRoute: String = "settings"
        override val navigationRoute: String = graphRoute
    }

    object Details : MainGraphRoutes() {
        const val CURRENCY_ID = "currencyId"
        private const val base = "details/"
        override val graphRoute: String = "$base{$CURRENCY_ID}"
        override val navigationRoute: String = base

        fun buildFullNavigationRoute(currencyId: String): String {
            return navigationRoute + currencyId
        }
    }

    object Test : MainGraphRoutes() {
        override val graphRoute: String = "test"
        override val navigationRoute: String = graphRoute
    }

    object Profile : MainGraphRoutes() {
        override val graphRoute: String = "profile"
        override val navigationRoute: String = graphRoute
    }
}