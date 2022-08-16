package com.example.cleanarchitectureexample.ui.details

import androidx.compose.runtime.Composable
import com.example.cleanarchitectureexample.navigation.navigateBack
import com.example.cleanarchitectureexample.ui.base.BaseComposeFragment
import com.example.cleanarchitectureexample.ui.base.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : BaseComposeFragment<DetailsViewModel>() {

    override val viewModel: DetailsViewModel by viewModel()

    @Composable
    override fun ContentView() = DetailsScreen(viewModel)

    override fun onBackPressed() {
        viewModel.backButtonClicked()
    }

    override fun initObservers() {
        super.initObservers()
        observe(viewModel.navigation, ::handleNavigation)
    }

    private fun handleNavigation(navigation: DetailsViewModel.Navigation) {
        when (navigation) {
            DetailsViewModel.Navigation.Back -> navigateBack()
        }
    }

}