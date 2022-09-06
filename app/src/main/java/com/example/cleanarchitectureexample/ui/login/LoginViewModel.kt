package com.example.cleanarchitectureexample.ui.login

import com.example.cleanarchitectureexample.BuildConfig
import com.example.cleanarchitectureexample.ui.base.ComposeBaseViewModel
import com.example.cleanarchitectureexample.ui.base.viewModelLaunch
import com.example.cleanarchitectureexample.ui.model.FieldState
import com.example.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ComposeBaseViewModel<LoginViewModel.Navigation>() {

    private val _loginFieldState = MutableStateFlow(getInitialLogin())
    val loginState = _loginFieldState.asStateFlow()

    private val _passwordFieldState = MutableStateFlow(getInitialPassword())
    val passwordState = _passwordFieldState.asStateFlow()

    override fun systemBackButtonClicked() {
        _navigation.trySend(Navigation.Back)
    }

    fun loginChangeRequested(newValue: String) {
        _loginFieldState.value = _loginFieldState.value.copy(text = newValue)
    }

    fun passwordChangeRequested(newValue: String) {
        _passwordFieldState.value = _passwordFieldState.value.copy(text = newValue)
    }

    fun loginButtonClicked() {
        viewModelLaunch(
            onProgressChanged = ::setProgressDialogVisibility,
            onError = { showErrorDialog(it) }
        ) {
            loginUseCase.execute(
                login = _loginFieldState.value.text.trim(),
                password = _passwordFieldState.value.text.trim()
            )
            _navigation.trySend(Navigation.Dashboard)
        }
    }

    private fun getInitialLogin(): FieldState {
        val text = if (BuildConfig.DEBUG) DEBUG_LOGIN else ""
        return FieldState(
            text = text,
            isEnabled = true,
            isValid = true
        )
    }

    private fun getInitialPassword(): FieldState {
        val text = if (BuildConfig.DEBUG) DEBUG_PASSWORD else ""
        return FieldState(
            text = text,
            isEnabled = true,
            isValid = true
        )
    }

    sealed class Navigation {
        object Dashboard : Navigation()
        object Back : Navigation()
    }

    companion object {
        private const val DEBUG_LOGIN = "user1"
        private const val DEBUG_PASSWORD = "test1"
    }

}

