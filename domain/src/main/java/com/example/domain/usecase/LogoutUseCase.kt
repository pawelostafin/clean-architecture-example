package com.example.domain.usecase

import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext

class LogoutUseCase(
    private val authRepository: AuthRepository
) {

    suspend fun execute() {
        nonCancellable {
            authRepository.logout()
        }
    }

}

suspend fun <T> nonCancellable(
    block: suspend CoroutineScope.() -> T
) = withContext(NonCancellable, block)