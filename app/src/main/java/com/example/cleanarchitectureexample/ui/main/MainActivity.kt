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
                startDestination = MainGraphRoutes.Splash.raw,
                route = MainGraphRoutes.Root.raw
            ) {
                fragment<SplashFragment>(route = MainGraphRoutes.Splash.raw)
                fragment<DashboardFragment>(route = MainGraphRoutes.Dashboard.raw)
                fragment<LoginFragment>(route = MainGraphRoutes.Login.raw)
                fragment<SettingsFragment>(route = MainGraphRoutes.Settings.raw)
                fragment<DetailsFragment>(route = MainGraphRoutes.Details.raw)
                fragment<TestFragment>(route = MainGraphRoutes.Test.raw)
                fragment<ProfileFragment>(route = MainGraphRoutes.Profile.raw)
            }
        }
    }

}

interface NavRoute {
    val raw: String
}

enum class MainGraphRoutes(override val raw: String) : NavRoute {
    Root("main_graph"),
    Splash("splash"),
    Login("login"),
    Dashboard("dashboard"),
    Settings("settings"),
    Details("details"),
    Test("test"),
    Profile("profile"),
}