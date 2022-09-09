package com.example.cleanarchitectureexample.ui.details

import com.example.cleanarchitectureexample.ui.main.NavControllerWithCloseAction
import com.example.cleanarchitectureexample.ui.splash.BaseNavigator


class DetailsNavigator(navControllerWithCloseAction: NavControllerWithCloseAction) :
    BaseNavigator<DetailsViewModel.Navigation>(navControllerWithCloseAction) {

    override fun handleNavigation(navigation: DetailsViewModel.Navigation) {
        when (navigation) {
            DetailsViewModel.Navigation.Back -> navigateBackOrCloseNavHolder()
        }
    }

}