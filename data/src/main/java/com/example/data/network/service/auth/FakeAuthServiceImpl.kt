package com.example.data.network.service.auth

import kotlinx.coroutines.delay

class FakeAuthServiceImpl : AuthService {

    override suspend fun login(login: String, password: String): Long {
        delay(2000)
        return FAKE_AUTH_DATA
            .firstOrNull { it.login == login && it.password == password }
            ?.userId
            ?: error("Invalid credentials. Try again")
    }

    companion object {
        private val FAKE_AUTH_DATA = listOf(
            FakeAuthData(
                login = "user1",
                password = "test1",
                userId = 1
            ),
            FakeAuthData(
                login = "user2",
                password = "test2",
                userId = 2
            ),
            FakeAuthData(
                login = "user3",
                password = "test3",
                userId = 3
            )
        )
    }

    private data class FakeAuthData(
        val login: String,
        val password: String,
        val userId: Long
    )

}