package com.example.domain.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.coroutines.CoroutineContext

class ReusableCoroutineScope(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineScope {

    private val lock = ReentrantLock()

    @Volatile
    private var currentCoroutineContext = createCoroutineContext()

    override val coroutineContext: CoroutineContext
        get() = lock.withLock { currentCoroutineContext }

    fun cancelAndReset() {
        lock.withLock {
            currentCoroutineContext.cancel()
            currentCoroutineContext = createCoroutineContext()
        }
    }

    private fun createCoroutineContext(): CoroutineContext {
        return SupervisorJob() + dispatcher
    }
}