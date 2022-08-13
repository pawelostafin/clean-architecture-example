package com.example.cleanarchitectureexample.ui.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseViewModel : ViewModel() {

    private val isInitialized = AtomicBoolean(false)

    private val _progressDialogVisibility = MutableStateFlow(false)
    val progressDialogVisibility = _progressDialogVisibility.asStateFlow()

    private val _errorDialogState = MutableStateFlow<ErrorDialogState>(ErrorDialogState.Hidden)
    val errorDialogState = _errorDialogState.asStateFlow()

    @CallSuper
    protected open fun initialize() = Unit

    fun initializeIfNeeded() {
        if (!isInitialized.getAndSet(true)) {
            initialize()
        }
    }

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

sealed class ErrorDialogState {
    object Hidden : ErrorDialogState()
    data class Visible(val throwable: Throwable) : ErrorDialogState()
}
