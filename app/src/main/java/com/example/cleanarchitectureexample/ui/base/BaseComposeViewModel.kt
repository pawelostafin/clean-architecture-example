package com.example.cleanarchitectureexample.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.atomic.AtomicBoolean

abstract class ComposeBaseViewModel<Navigation> : ViewModel() {

    protected val _navigation = EventChannel<Navigation>()
    val navigation = _navigation.asFlow()

    abstract fun systemBackButtonClicked()

    private val isInitialized = AtomicBoolean(false)

    private val _progressDialogVisibility = MutableStateFlow(false)
    val progressDialogVisibility = _progressDialogVisibility.asStateFlow()

    private val _errorDialogState = MutableStateFlow<ErrorDialogState>(ErrorDialogState.Hidden)
    val errorDialogState = _errorDialogState.asStateFlow()

    protected fun setProgressDialogVisibility(isVisible: Boolean) {
        _progressDialogVisibility.value = isVisible
    }

    protected fun showErrorDialog(throwable: Throwable) {
        _errorDialogState.value = ErrorDialogState.Visible(throwable = throwable)
    }

    fun errorDialogDismissRequested() {
        _errorDialogState.value = ErrorDialogState.Hidden
    }

}