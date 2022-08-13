package com.example.data.network.model

data class UserDetailsDto(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val imageUrl: String?,
    val description: String?
)