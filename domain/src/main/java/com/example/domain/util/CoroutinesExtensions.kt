package com.example.domain.util

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

fun CoroutineScope.supportLaunch(
    onProgressChanged: suspend (isInProgress: Boolean) -> Unit = {},
    onError: suspend (throwable: Throwable) -> Unit = {},
    onCancel: suspend () -> Unit = {},
    action: suspend CoroutineScope.() -> Unit,
) = this.launch {
    onProgressChanged.invoke(true)
    kotlin
        .runCatching { action.invoke(this) }
        .onFailure {
            Timber.e(it)
            if (it is CancellationException) {
                onCancel.invoke()
            } else {
                onError.invoke(it)
            }
        }
    onProgressChanged.invoke(false)
}
