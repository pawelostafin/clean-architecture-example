package com.example.cleanarchitectureexample.ui.profile

import androidx.compose.runtime.Composable
import com.example.cleanarchitectureexample.navigation.NavTransition
import com.example.cleanarchitectureexample.navigation.navigate
import com.example.cleanarchitectureexample.navigation.navigateBack
import com.example.cleanarchitectureexample.ui.base.BaseComposeFragment
import com.example.cleanarchitectureexample.ui.base.observe
import com.example.cleanarchitectureexample.ui.main.MainGraphRoutes
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseComposeFragment<ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModel()

    override fun onBackPressed() {
        viewModel.backButtonClicked()
    }

    @Composable
    override fun ContentView() = ProfileScreen(viewModel)

    override fun initObservers() {
        super.initObservers()
        observe(viewModel.navigation, ::handleNavigation)
    }

    private fun handleNavigation(navigation: ProfileViewModel.Navigation) {
        when (navigation) {
            ProfileViewModel.Navigation.Back -> navigateBack()
            ProfileViewModel.Navigation.Login -> navigateToLogin()
            ProfileViewModel.Navigation.Settings -> navigateToSettings()
        }
    }

    private fun navigateToLogin() {
        navigate(
            route = MainGraphRoutes.Login,
            popUpToRoute = MainGraphRoutes.Root,
            transition = NavTransition.FADE
        )
    }

    private fun navigateToSettings() {
        navigate(
            route = MainGraphRoutes.Settings,
            transition = NavTransition.RIGHT
        )
    }

}