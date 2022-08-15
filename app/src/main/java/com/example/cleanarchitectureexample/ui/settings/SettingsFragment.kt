package com.example.cleanarchitectureexample.ui.settings

import androidx.compose.runtime.Composable
import com.example.cleanarchitectureexample.navigation.navigateBack
import com.example.cleanarchitectureexample.ui.base.BaseComposeFragment
import com.example.cleanarchitectureexample.ui.base.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseComposeFragment<SettingsViewModel>() {

    override val viewModel: SettingsViewModel by viewModel()

    @Composable
    override fun ContentView() = SettingsScreen(viewModel)

    override fun initObservers() {
        super.initObservers()
        observe(viewModel.navigation, ::handleNavigation)
    }

    override fun onBackPressed() {
        viewModel.backButtonClicked()
    }

    private fun handleNavigation(navigation: SettingsViewModel.Navigation) {
        when (navigation) {
            SettingsViewModel.Navigation.Back -> navigateBack()
        }
    }

}