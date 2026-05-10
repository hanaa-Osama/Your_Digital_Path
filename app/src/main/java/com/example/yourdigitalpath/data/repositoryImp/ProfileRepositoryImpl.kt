package com.example.yourdigitalpath.data.repositoryImp

import com.example.yourdigitalpath.data.local.Dao.UserProfileDao
import com.example.yourdigitalpath.data.mapper.toDomain
import com.example.yourdigitalpath.data.mapper.toEntity
import com.example.yourdigitalpath.domain.model.UserProfileModel
import com.example.yourdigitalpath.domain.repository.ProfileRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val userProfileDao: UserProfileDao
) : ProfileRepository {

    override suspend fun getUserProfile(): UserProfileModel? {
        val localProfile = userProfileDao.getUserProfile()?.toDomain()
        if (localProfile != null) return localProfile
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        return firebaseUser?.let {
            val nationalId = it.email?.replace("@digitalpath.app", "") ?: ""
            UserProfileModel(
                nationalId  = nationalId,
                name        = it.displayName ?: "",
                email       = it.email ?: "",
                phoneNumber = "",
                governorate = null
            )
        }
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