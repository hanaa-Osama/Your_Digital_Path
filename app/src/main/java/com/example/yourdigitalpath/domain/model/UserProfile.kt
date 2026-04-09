package com.example.yourdigitalpath.domain.model

data class UserProfile(
    val name: String,
    val nationalId: String,
    val email: String,
    val phoneNumber: String,
    val governorate: String?
)
