package com.example.cleanarchitectureexample.ui.dashboard

import com.example.cleanarchitectureexample.ui.base.BaseViewModel
import com.example.cleanarchitectureexample.ui.base.EventChannel
import com.example.cleanarchitectureexample.ui.base.asFlow
import com.example.cleanarchitectureexample.ui.base.viewModelLaunch
import com.example.domain.usecase.GetUserDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel(
    private val getUserDetailsUseCase: GetUserDetailsUseCase
) : BaseViewModel() {

    private val _navigation = EventChannel<Navigation>()
    val navigation = _navigation.asFlow()

    private val _profileButtonState = MutableStateFlow<ProfileButtonState>(ProfileButtonState.InProgress)
    val profileButtonState = _profileButtonState.asStateFlow()

    override fun initialize() {
        super.initialize()

        viewModelLaunch(
            onProgressChanged = { isInProgress ->
                if (isInProgress) {
                    _profileButtonState.value = ProfileButtonState.InProgress
                }
            },
            onError = ::showErrorDialog,
        ) {
            val userDetails = getUserDetailsUseCase.execute()
            val profileButtonState = ProfileButtonState.Data(
                imageUrl = userDetails.imageUrl,
                firstLetterOfFirstName = userDetails.firstLetterOfFirstName
            )
            _profileButtonState.value = profileButtonState
        }
    }

    fun backButtonClicked() {
        _navigation.trySend(Navigation.Back)
    }

    fun profileButtonClicked() {
        _navigation.trySend(Navigation.Profile)
    }

    sealed class Navigation {
        object Back : Navigation()
        object Profile : Navigation()
    }

}

sealed class ProfileButtonState {

    object InProgress : ProfileButtonState()

    data class Data(
        val imageUrl: String?,
        val firstLetterOfFirstName: String
    ) : ProfileButtonState()

}
