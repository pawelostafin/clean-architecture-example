package com.example.cleanarchitectureexample.ui.login

import com.example.cleanarchitectureexample.BuildConfig
import com.example.cleanarchitectureexample.ui.base.BaseViewModel
import com.example.cleanarchitectureexample.ui.base.EventChannel
import com.example.cleanarchitectureexample.ui.base.asFlow
import com.example.cleanarchitectureexample.ui.base.viewModelLaunch
import com.example.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    private val _navigation = EventChannel<Navigation>()
    val navigation = _navigation.asFlow()

    private val _login = MutableStateFlow(getInitialLogin())
    val login = _login.asStateFlow()

    private val _password = MutableStateFlow(getInitialPassword())
    val password = _password.asStateFlow()

    private val _loginButtonState = MutableStateFlow(LoginButtonState.CLICKABLE)
    val loginButtonState = _loginButtonState.asStateFlow()

    private val _loginErrorDialogVisible = MutableStateFlow(false)
    val loginErrorDialogVisible = _loginErrorDialogVisible.asStateFlow()

    fun loginChangeRequested(newValue: String) {
        _login.value = _login.value.copy(text = newValue)
    }

    fun passwordChangeRequested(newValue: String) {
        _password.value = _password.value.copy(text = newValue)
    }

    fun backButtonClicked() {
        _navigation.trySend(Navigation.Back)
    }

    fun loginButtonClicked() {
        viewModelLaunch(
            onProgressChanged = ::setFormInProgress,
            onError = { _loginErrorDialogVisible.value = true }
        ) {
            loginUseCase.execute(
                login = _login.value.text,
                password = _password.value.text
            )
            _navigation.trySend(Navigation.Dashboard)
        }
    }

    private fun setFormInProgress(inProgress: Boolean) {
        _login.value = _login.value.copy(isEnabled = !inProgress)
        _password.value = _password.value.copy(isEnabled = !inProgress)
        _loginButtonState.value = if (inProgress) LoginButtonState.IN_PROGRESS else LoginButtonState.CLICKABLE
    }

    fun loginErrorDialogDismissed() {
        _loginErrorDialogVisible.value = false
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
        private const val DEBUG_LOGIN = "user"
        private const val DEBUG_PASSWORD = "test1"
    }

}

data class FieldState(
    val text: String,
    val isEnabled: Boolean,
    val isValid: Boolean
)

enum class LoginButtonState {
    IN_PROGRESS,
    CLICKABLE
}