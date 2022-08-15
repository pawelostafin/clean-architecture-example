package com.example.data.network.service.userdetails

import com.example.data.network.model.UserDetailsDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicBoolean

class FakeUserDetailsService : UserDetailsService {

    private val mutex = Mutex()
    private val isFirstRequest = AtomicBoolean(true)

    override suspend fun getUserDetails(userId: Long): UserDetailsDto {
        mutex.withLock {
            if (isFirstRequest.getAndSet(false)) {
                delay(1000)
            }
        }
        return FAKE_USER_DETAILS
            .firstOrNull { it.id == userId }
            ?: error("userId not found")
    }

    companion object {
        private val FAKE_USER_DETAILS = listOf(
            UserDetailsDto(
                id = 1,
                firstName = "Tony",
                lastName = "Spagetti",
                imageUrl = "https://static.posters.cz/image/1300/plakaty/pokemon-pikachu-neon-i71936.jpg",
                description = "user description 1"
            ),
            UserDetailsDto(
                id = 2,
                firstName = "Frank",
                lastName = "Jacobs",
                imageUrl = null,
                description = "user description 2"
            ),
            UserDetailsDto(
                id = 3,
                firstName = "Tom",
                lastName = "Slovak",
                imageUrl = null,
                description = "user description 3"
            )
        )
    }

}