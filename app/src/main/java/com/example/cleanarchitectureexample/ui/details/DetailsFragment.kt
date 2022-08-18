package com.example.cleanarchitectureexample.ui.details

import android.os.Bundle
import androidx.compose.runtime.Composable
import com.example.cleanarchitectureexample.navigation.navigateBack
import com.example.cleanarchitectureexample.ui.base.BaseComposeFragment
import com.example.cleanarchitectureexample.ui.base.observe
import com.example.cleanarchitectureexample.ui.main.MainGraphRoutes
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment : BaseComposeFragment<DetailsViewModel>() {

    override val viewModel: DetailsViewModel by viewModel(parameters = {
        parametersOf(requireArguments().requireString(MainGraphRoutes.Details.CURRENCY_ID))
    })

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

fun Bundle.requireString(key: String): String {
    return getString(key)!!
}