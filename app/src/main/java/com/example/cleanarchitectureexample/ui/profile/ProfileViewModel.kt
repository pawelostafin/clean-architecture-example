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

    private val _profileImageUrl = MutableStateFlow<String?>(null)
    val profileImageUrl = _profileImageUrl.asStateFlow()

    override fun initialize() {
        super.initialize()

        viewModelLaunch {
            val userDetails = getUserDetailsUseCase.execute()
            _profileImageUrl.value = userDetails.imageUrl
        }
    }

    fun backButtonClicked() {
        _navigation.trySend(Navigation.Back)
    }

    fun logoutButtonClicked() {
        viewModelLaunch(
            onProgressChanged = ::setProgressDialogVisibility,
            onError = ::showErrorDialog
        ) {
            logoutUseCase.execute()
            _navigation.trySend(Navigation.Login)
        }
    }

    sealed class Navigation {
        object Back : Navigation()
        object Login : Navigation()
    }

}