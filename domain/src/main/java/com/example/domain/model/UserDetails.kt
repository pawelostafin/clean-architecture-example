package com.example.domain.model

data class UserDetails(
    val userId: UserId,
    val firstName: String,
    val lastName: String,
    val imageUrl: String?,
    val description: String?
) {

    val firstLetterOfFirstName: String
        get() {
            return firstName
                .takeIf { it.isNotBlank() }
                ?.take(1)
                ?.uppercase()
                ?: ""
        }

}