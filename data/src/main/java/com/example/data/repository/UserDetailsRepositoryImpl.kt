package com.example.data.repository

import com.example.data.holder.SessionDataHolder
import com.example.data.network.model.UserDetailsDto
import com.example.data.network.service.userdetails.UserDetailsService
import com.example.domain.model.UserDetails
import com.example.domain.model.UserId
import com.example.domain.repository.UserDetailsRepository

class UserDetailsRepositoryImpl(
    private val userDetailsService: UserDetailsService,
    private val sessionDataHolder: SessionDataHolder
) : UserDetailsRepository {

    override suspend fun getUserDetails(): UserDetails {
        val userId = sessionDataHolder.getUserId() ?: error("userId not found")
        val result = userDetailsService.getUserDetails(userId = userId.value)
        return result.toDomainModel()
    }

}

private fun UserDetailsDto.toDomainModel(): UserDetails {
    return UserDetails(
        userId = UserId(id),
        firstName = firstName,
        lastName = lastName,
        imageUrl = imageUrl,
        description = description,
    )
}