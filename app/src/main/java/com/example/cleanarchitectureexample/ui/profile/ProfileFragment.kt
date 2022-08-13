package com.example.cleanarchitectureexample.ui.profile

import androidx.compose.runtime.Composable
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.navigation.navigate
import com.example.cleanarchitectureexample.navigation.navigateBack
import com.example.cleanarchitectureexample.ui.base.BaseComposeFragment
import com.example.cleanarchitectureexample.ui.base.observe
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
        }
    }

    private fun navigateToLogin() {
        navigate(
            fragmentResId = R.id.loginFragment,
            popUpTo = R.id.main_graph
        )
    }

}