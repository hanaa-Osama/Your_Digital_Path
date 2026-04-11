package com.example.yourdigitalpath.domain.model

data class UserProfileModel(
    val name: String,
    val nationalId: String,
    val email: String,
    val phoneNumber: String,
    val governorate: String?
)
