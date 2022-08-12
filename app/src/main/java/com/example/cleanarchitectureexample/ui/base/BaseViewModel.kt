package com.example.cleanarchitectureexample.ui.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseViewModel : ViewModel() {

    private val isInitialized = AtomicBoolean(false)

    @CallSuper
    protected open fun initialize() = Unit

    fun initializeIfNeeded() {
        if (!isInitialized.getAndSet(true)) {
            initialize()
        }
    }

}