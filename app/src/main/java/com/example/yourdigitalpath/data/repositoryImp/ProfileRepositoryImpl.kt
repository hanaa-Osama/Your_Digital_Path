package com.example.yourdigitalpath.data.repositoryImp

import com.example.yourdigitalpath.data.local.Dao.UserProfileDao
import com.example.yourdigitalpath.data.mapper.toDomain
import com.example.yourdigitalpath.data.mapper.toEntity
import com.example.yourdigitalpath.domain.model.UserProfileModel
import com.example.yourdigitalpath.domain.repository.ProfileRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val userProfileDao: UserProfileDao,
    private val firestore: FirebaseFirestore,
    private val auth: com.google.firebase.auth.FirebaseAuth
) : ProfileRepository {

    override suspend fun getUserProfile(): UserProfileModel? {
        val localProfile = userProfileDao.getUserProfile()?.toDomain()
        if (localProfile != null) return localProfile

        val uid = auth.currentUser?.uid ?: return null

        return try {
            val snapshot = firestore.collection("users").document(uid).get().await()
            val remoteProfile = snapshot.toObject(UserProfileModel::class.java)
            remoteProfile?.let {
                userProfileDao.insertUserProfile(it.toEntity())
            }
            remoteProfile
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun updateProfile(profile: UserProfileModel): Boolean {
        return try {
            val uid = auth.currentUser?.uid ?: return false
            firestore.collection("users").document(uid).set(profile).await()
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