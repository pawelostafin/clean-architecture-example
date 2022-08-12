package com.example.data.network.service.auth

interface AuthService {

    suspend fun login(
        login: String,
        password: String
    ): Long

}