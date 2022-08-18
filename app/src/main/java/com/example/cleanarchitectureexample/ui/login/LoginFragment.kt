package com.example.cleanarchitectureexample.ui.login

import androidx.compose.runtime.Composable
import com.example.cleanarchitectureexample.navigation.NavTransition
import com.example.cleanarchitectureexample.navigation.navigate
import com.example.cleanarchitectureexample.navigation.navigateBack
import com.example.cleanarchitectureexample.ui.base.BaseComposeFragment
import com.example.cleanarchitectureexample.ui.base.observe
import com.example.cleanarchitectureexample.ui.main.MainGraphRoutes
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseComposeFragment<LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModel()

    @Composable
    override fun ContentView() = LoginScreen(viewModel)

    override fun onBackPressed() {
        viewModel.backButtonClicked()
    }

    override fun initObservers() {
        super.initObservers()
        observe(viewModel.navigation, ::handleNavigation)
    }

    private fun handleNavigation(navigation: LoginViewModel.Navigation) {
        when (navigation) {
            LoginViewModel.Navigation.Back -> navigateBack()
            LoginViewModel.Navigation.Dashboard -> navigateToDashboard()
        }
    }

    private fun navigateToDashboard() {
        navigate(
            route = MainGraphRoutes.Dashboard.navigationRoute,
            popUpToRoute = MainGraphRoutes.Root.navigationRoute,
            transition = NavTransition.FADE
        )
    }

}