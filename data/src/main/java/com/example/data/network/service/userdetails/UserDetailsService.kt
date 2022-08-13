package com.example.data.network.service.userdetails

import com.example.data.network.model.UserDetailsDto

interface UserDetailsService {

    suspend fun getUserDetails(userId: Long): UserDetailsDto

}