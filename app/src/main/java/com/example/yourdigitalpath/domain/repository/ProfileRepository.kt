package com.example.yourdigitalpath.domain.repository

import com.example.yourdigitalpath.domain.model.UserProfile

interface ProfileRepository {
    suspend fun getUserProfile(): UserProfile
    suspend fun updateProfile(profile: UserProfile): Boolean

    suspend fun clearUserData()
}