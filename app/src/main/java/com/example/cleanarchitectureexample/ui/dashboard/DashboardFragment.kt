package com.example.cleanarchitectureexample.ui.dashboard

import androidx.compose.runtime.Composable
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.navigation.NavTransition
import com.example.cleanarchitectureexample.navigation.navigate
import com.example.cleanarchitectureexample.navigation.navigateBack
import com.example.cleanarchitectureexample.ui.base.BaseComposeFragment
import com.example.cleanarchitectureexample.ui.base.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseComposeFragment<DashboardViewModel>() {

    override val viewModel: DashboardViewModel by viewModel()

    @Composable
    override fun ContentView() = DashboardScreen(viewModel = viewModel)

    override fun initObservers() {
        super.initObservers()
        observe(viewModel.navigation, ::handleNavigation)
    }

    private fun handleNavigation(navigation: DashboardViewModel.Navigation) {
        when (navigation) {
            DashboardViewModel.Navigation.Back -> navigateBack()
            DashboardViewModel.Navigation.Profile -> navigateToProfile()
            DashboardViewModel.Navigation.Details -> navigateToDetails()
        }
    }

    override fun onBackPressed() {
        viewModel.backButtonClicked()
    }

    private fun navigateToProfile() {
        navigate(
            fragmentResId = R.id.profileFragment,
            transition = NavTransition.BOTTOM
        )
    }

    private fun navigateToDetails(){
        navigate(
            fragmentResId = R.id.detailsFragment,
            transition = NavTransition.RIGHT
        )
    }

}