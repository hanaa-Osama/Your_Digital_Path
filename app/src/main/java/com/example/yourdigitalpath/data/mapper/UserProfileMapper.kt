package com.example.yourdigitalpath.data.mapper

import com.example.yourdigitalpath.data.local.entity.UserProfileEntity
import com.example.yourdigitalpath.domain.model.UserProfile

fun UserProfileEntity.toDomain(): UserProfile {
    return UserProfile(
        nationalId = nationalId,
        name = name,
        email = email,
        phoneNumber = phoneNumber,
        governorate = governorate
    )
}

fun UserProfile.toEntity(): UserProfileEntity {
    return UserProfileEntity(
        nationalId = nationalId,
        name = name,
        email = email,
        phoneNumber = phoneNumber,
        governorate = governorate
    )
}