package com.example.yourdigitalpath.data.repositoryImp

import com.example.yourdigitalpath.data.local.Dao.UserProfileDao
import com.example.yourdigitalpath.data.mapper.toDomain
import com.example.yourdigitalpath.data.mapper.toEntity
import com.example.yourdigitalpath.domain.model.UserProfileModel
import com.example.yourdigitalpath.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val userProfileDao: UserProfileDao
) : ProfileRepository {

    override suspend fun getUserProfile(): UserProfileModel? {
        // TODO: remove fallback before production
        return userProfileDao.getUserProfile()?.toDomain() ?: UserProfileModel(
            nationalId = "2990115001234",
            name = "هناء اسامة سمير",
            email = "hanaa@email.com",
            phoneNumber = "01012345678",
            governorate = "القاهرة"
        )
    }

    override suspend fun updateProfile(profile: UserProfileModel): Boolean {
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