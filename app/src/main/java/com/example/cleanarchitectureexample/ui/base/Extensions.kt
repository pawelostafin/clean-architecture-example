package com.example.cleanarchitectureexample.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@Suppress("FunctionName")
fun <T> EventChannel() = Channel<T>(Channel.CONFLATED)

fun <T> Channel<T>.asFlow() = receiveAsFlow()

fun <T> ViewModel.viewModelObserveFlow(
    flowProvider: suspend () -> Flow<T>,
    onProgressChanged: (Boolean) -> Unit = {},
    onError: suspend (Throwable) -> Unit = {},
    onCancel: suspend () -> Unit = {},
    onEach: suspend (T) -> Unit = {}
) {
    observeFlow(
        flowProvider = flowProvider,
        scope = viewModelScope,
        onProgressChanged = onProgressChanged,
        onError = onError,
        onCancel = onCancel,
        onEach = onEach
    )
}

fun ViewModel.viewModelLaunch(
    onProgressChanged: suspend (isInProgress: Boolean) -> Unit = {},
    onError: suspend (throwable: Throwable) -> Unit = {},
    onCancel: suspend (CancellationException) -> Unit = {},
    action: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch {
        onProgressChanged.invoke(true)
        kotlin
            .runCatching { action.invoke(this) }
            .onFailure {
                Timber.e(it)
                if (it is CancellationException) {
                    onCancel.invoke(it)
                } else {
                    onError.invoke(it)
                }
            }
        onProgressChanged.invoke(false)
    }
}

fun <T> CancellableContinuation<T>.ifActive(action: CancellableContinuation<T>.() -> Unit) {
    if (isActive) run(action)
}

fun CoroutineScope.launchCatching(
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

fun <T> observeFlow(
    flowProvider: suspend () -> Flow<T>,
    scope: CoroutineScope,
    onProgressChanged: (Boolean) -> Unit = {},
    onError: suspend (Throwable) -> Unit = {},
    onCancel: suspend () -> Unit = {},
    onEach: suspend (T) -> Unit = {}
): Job {
    return scope.launchCatching(
        onError = onError,
        onCancel = onCancel
    ) {
        onProgressChanged.invoke(true)
        flowProvider.invoke()
            .onEach {
                onProgressChanged.invoke(false)
                onEach.invoke(it)
            }
            .catch {
                onProgressChanged.invoke(false)
                Timber.e(it)
                if (it is CancellationException) {
                    onCancel.invoke()
                } else {
                    onError.invoke(it)
                }
            }
            .collect()
    }
}


@Suppress("FunctionName")
fun <T> BehaviorFlow() = MutableSharedFlow<T>(
    replay = 1,
    extraBufferCapacity = 0,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)

@Suppress("FunctionName")
fun <T> BehaviorFlow(initialValue: T) = BehaviorFlow<T>().also { it.tryEmit(initialValue) }

@Suppress("FunctionName")
fun <T> PublishFlow() = MutableSharedFlow<T>(
    replay = 0,
    extraBufferCapacity = 1,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)

suspend fun MutableSharedFlow<Unit>.emit() = emit(Unit)

fun MutableSharedFlow<Unit>.tryEmit() = tryEmit(Unit)

fun <T> MutableSharedFlow<T>.asFlow(): Flow<T> = this

fun <T> MutableSharedFlow<T>.requireValue(): T =
    replayCache.firstOrNull() ?: error("required flow value missing")

val <T> MutableSharedFlow<T>.value: T?
    get() = replayCache.firstOrNull()

//  https://github.com/Kotlin/kotlinx.coroutines/issues/1767#issuecomment-577158308
@Suppress("UNCHECKED_CAST")
fun <T, R> Flow<T>.zipWithNext(transform: (T, T) -> R): Flow<R> = flow {
    var prev: Any? = UNDEFINED
    collect { value ->
        if (prev !== UNDEFINED) emit(transform(prev as T, value))
        prev = value
    }
}

private object UNDEFINED

suspend fun delaySeconds(seconds: Int) = delay(seconds * 1000L)