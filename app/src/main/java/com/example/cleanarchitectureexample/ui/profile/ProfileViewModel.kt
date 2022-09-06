package com.example.cleanarchitectureexample.ui.profile

import com.example.cleanarchitectureexample.ui.base.ComposeBaseViewModel
import com.example.cleanarchitectureexample.ui.base.viewModelLaunch
import com.example.domain.usecase.GetUserDetailsUseCase
import com.example.domain.usecase.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val getUserDetailsUseCase: GetUserDetailsUseCase
) : ComposeBaseViewModel<ProfileViewModel.Navigation>() {


    private val _profileInfo = MutableStateFlow<ProfileInfo?>(null)
    val profileInfo = _profileInfo.asStateFlow()

    init {
        viewModelLaunch {
            val userDetails = getUserDetailsUseCase.execute()
            _profileInfo.value = ProfileInfo(
                fullName = "${userDetails.firstName} ${userDetails.lastName}".trim(),
                description = userDetails.description,
                imageUrl = userDetails.imageUrl,
                firstLetterOfTheFirstName = userDetails.firstLetterOfFirstName
            )
        }
    }

    fun logoutButtonClicked() {
        viewModelLaunch {
            logoutUseCase.execute()
            _navigation.trySend(Navigation.Login)
        }
    }

    fun closeButtonClicked() {
        _navigation.trySend(Navigation.Back)
    }

    fun settingsButtonClicked() {
        _navigation.trySend(Navigation.Settings)
    }

    sealed class Navigation {
        object Back : Navigation()
        object Login : Navigation()
        object Settings : Navigation()
    }

    override fun systemBackButtonClicked() {
        _navigation.trySend(Navigation.Back)
    }

}

data class ProfileInfo(
    val fullName: String,
    val description: String?,
    val imageUrl: String?,
    val firstLetterOfTheFirstName: String
)