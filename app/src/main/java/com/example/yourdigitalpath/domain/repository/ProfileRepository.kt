package com.example.yourdigitalpath.domain.repository

import com.example.yourdigitalpath.domain.model.UserProfileModel

interface ProfileRepository {
    suspend fun getUserProfile(): UserProfileModel?
    suspend fun updateProfile(profile: UserProfileModel): Boolean

    suspend fun clearUserData()
}