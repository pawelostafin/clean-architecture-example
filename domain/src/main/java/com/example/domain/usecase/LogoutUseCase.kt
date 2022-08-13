package com.example.domain.usecase

import com.example.domain.repository.AuthRepository

class LogoutUseCase(
    private val authRepository: AuthRepository
) {

    suspend fun execute() {
        authRepository.logout()
    }

}