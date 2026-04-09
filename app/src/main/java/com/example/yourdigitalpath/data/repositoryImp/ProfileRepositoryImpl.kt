package com.example.yourdigitalpath.data.repositoryImp

import com.example.yourdigitalpath.data.local.Dao.UserProfileDao
import com.example.yourdigitalpath.data.mapper.toDomain
import com.example.yourdigitalpath.data.mapper.toEntity
import com.example.yourdigitalpath.domain.model.UserProfile
import com.example.yourdigitalpath.domain.repository.ProfileRepository
import javax.inject.Inject


class ProfileRepositoryImpl @Inject constructor(
    private val userProfileDao: UserProfileDao
) : ProfileRepository {
    override suspend fun getUserProfile(): UserProfile {
        val entity = userProfileDao.getUserProfile()
        return entity?.toDomain() ?: UserProfile("", "", "", "", "")
    }

    override suspend fun updateProfile(profile: UserProfile): Boolean {
        return try {
            userProfileDao.insertUserProfile(profile.toEntity())
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun clearUserData() {
        userProfileDao.clearUserProfile()
    }

}