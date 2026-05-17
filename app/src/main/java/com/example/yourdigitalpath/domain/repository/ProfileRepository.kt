package com.example.yourdigitalpath.domain.repository

import com.example.yourdigitalpath.domain.model.UserProfileModel
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getUserProfile(): UserProfileModel?
    fun getUserProfileFlow(): Flow<UserProfileModel?>
    suspend fun updateProfile(profile: UserProfileModel): Boolean

    suspend fun clearUserData()
}