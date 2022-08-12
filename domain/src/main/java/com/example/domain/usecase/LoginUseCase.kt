package com.example.domain.usecase

import com.example.domain.model.UserId
import com.example.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {

    suspend fun execute(
        login: String,
        password: String
    ): UserId {
        return authRepository.login(
            login = login,
            password = password
        )
    }

}