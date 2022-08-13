package com.example.cleanarchitectureexample.ui.splash

import androidx.compose.runtime.Composable
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.navigation.navigate
import com.example.cleanarchitectureexample.navigation.navigateBack
import com.example.cleanarchitectureexample.ui.base.BaseComposeFragment
import com.example.cleanarchitectureexample.ui.base.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseComposeFragment<SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModel()

    @Composable
    override fun ContentView() = SplashScreen(viewModel)

    override fun onBackPressed() {
        viewModel.backButtonClicked()
    }

    override fun initObservers() {
        super.initObservers()
        observe(viewModel.navigation, ::handleNavigation)
    }

    private fun handleNavigation(navigation: SplashViewModel.Navigation) {
        when (navigation) {
            SplashViewModel.Navigation.Back -> navigateBack()
            SplashViewModel.Navigation.Dashboard -> navigateToDashboard()
            SplashViewModel.Navigation.Login -> navigateToLogin()
        }
    }

    private fun navigateToDashboard() {
        navigate(
            fragmentResId = R.id.dashboardFragment,
            popUpTo = R.id.main_graph
        )
    }

    private fun navigateToLogin() {
        navigate(
            fragmentResId = R.id.loginFragment,
            popUpTo = R.id.main_graph
        )
    }

}