package com.example.data.repository

import com.example.data.network.service.auth.AuthService
import com.example.domain.model.UserId
import com.example.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authService: AuthService
) : AuthRepository {

    override suspend fun login(
        login: String,
        password: String
    ): UserId {
        val userId = authService.login(
            login = login,
            password = password
        )
        return UserId(userId)
    }

}