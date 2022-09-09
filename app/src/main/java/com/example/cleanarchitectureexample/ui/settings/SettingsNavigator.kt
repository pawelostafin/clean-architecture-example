package com.example.cleanarchitectureexample.ui.settings

import com.example.cleanarchitectureexample.ui.main.NavControllerWithCloseAction
import com.example.cleanarchitectureexample.ui.splash.BaseNavigator

class SettingsNavigator(navControllerWithCloseAction: NavControllerWithCloseAction) :
    BaseNavigator<SettingsViewModel.Navigation>(navControllerWithCloseAction) {

    override fun handleNavigation(navigation: SettingsViewModel.Navigation) {
        when (navigation) {
            SettingsViewModel.Navigation.Back -> navigateBackOrCloseNavHolder()
        }
    }

}