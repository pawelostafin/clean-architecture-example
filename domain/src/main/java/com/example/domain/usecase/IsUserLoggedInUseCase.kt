package com.example.domain.usecase

import com.example.domain.repository.AuthRepository

class IsUserLoggedInUseCase(
    private val authRepository: AuthRepository
) {

    suspend fun execute(): Boolean {
        return authRepository.isUserLoggedIn()
    }

}