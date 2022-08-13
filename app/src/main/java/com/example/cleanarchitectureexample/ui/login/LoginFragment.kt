package com.example.cleanarchitectureexample.ui.login

import androidx.compose.runtime.Composable
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.navigation.navigateBack
import com.example.cleanarchitectureexample.ui.base.BaseComposeFragment
import com.example.cleanarchitectureexample.ui.base.observe
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
        findNavController().navigate(
            resId = R.id.dashboardFragment,
            args = null,
            navOptions = navOptions { popUpTo(id = R.id.main_graph) }
        )
    }

}