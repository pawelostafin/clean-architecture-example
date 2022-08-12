package com.example.domain.repository

import com.example.domain.model.UserId

interface AuthRepository {

    suspend fun login(
        login: String,
        password: String
    ): UserId

}