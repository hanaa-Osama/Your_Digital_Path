package com.example.yourdigitalpath.data.mapper

import com.example.yourdigitalpath.data.local.entity.UserProfileEntity
import com.example.yourdigitalpath.domain.model.UserProfileModel

fun UserProfileEntity.toDomain(): UserProfileModel {
    return UserProfileModel(
        nationalId = nationalId,
        name = name,
        email = email,
        phoneNumber = phoneNumber,
        governorate = governorate
    )
}

fun UserProfileModel.toEntity(): UserProfileEntity {
    return UserProfileEntity(
        nationalId = nationalId,
        name = name,
        email = email,
        phoneNumber = phoneNumber,
        governorate = governorate
    )
}