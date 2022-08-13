package com.example.domain.usecase

import com.example.domain.model.UserDetails
import com.example.domain.repository.UserDetailsRepository

class GetUserDetailsUseCase(
    private val userDetailsRepository: UserDetailsRepository
) {

    suspend fun execute(): UserDetails {
        return userDetailsRepository.getUserDetails()
    }

}