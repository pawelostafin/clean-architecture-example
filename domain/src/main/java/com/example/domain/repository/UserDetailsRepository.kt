package com.example.domain.repository

import com.example.domain.model.UserDetails

interface UserDetailsRepository {

    suspend fun getUserDetails(): UserDetails

}