package com.example.cleanarchitectureexample.ui.profile

import com.example.cleanarchitectureexample.ui.base.BaseViewModel
import com.example.cleanarchitectureexample.ui.base.EventChannel
import com.example.cleanarchitectureexample.ui.base.asFlow
import com.example.cleanarchitectureexample.ui.base.viewModelLaunch
import com.example.domain.usecase.GetUserDetailsUseCase
import com.example.domain.usecase.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val getUserDetailsUseCase: GetUserDetailsUseCase
) : BaseViewModel() {

    private val _navigation = EventChannel<Navigation>()
    val navigation = _navigation.asFlow()

    private val _profileInfo = MutableStateFlow<ProfileInfo?>(null)
    val profileInfo = _profileInfo.asStateFlow()

    override fun initialize() {
        super.initialize()

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

    fun backButtonClicked() {
        _navigation.trySend(Navigation.Back)
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

    sealed class Navigation {
        object Back : Navigation()
        object Login : Navigation()
    }

}

data class ProfileInfo(
    val fullName: String,
    val description: String?,
    val imageUrl: String?,
    val firstLetterOfTheFirstName: String
)