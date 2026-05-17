package com.example.yourdigitalpath.data.repositoryImp

import android.content.Context
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.data.local.Dao.UserProfileDao
import com.example.yourdigitalpath.data.mapper.toDomain
import com.example.yourdigitalpath.data.mapper.toEntity
import com.example.yourdigitalpath.domain.model.UserProfileModel
import com.example.yourdigitalpath.domain.repository.ProfileRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val userProfileDao: UserProfileDao,
    private val firestore: FirebaseFirestore,
    private val auth: com.google.firebase.auth.FirebaseAuth,
    @ApplicationContext
    private val context: Context
) : ProfileRepository {

    override fun getUserProfileFlow(): Flow<UserProfileModel?> {
        return userProfileDao.getUserProfileFlow().map { it?.toDomain() }
    }

    override suspend fun getUserProfile(): UserProfileModel? {
        val uid = auth.currentUser?.uid ?: return null

        return try {
            val snapshot = firestore.collection("users").document(uid).get().await()
            if (!snapshot.exists()) return userProfileDao.getUserProfile()?.toDomain()

            val profile = UserProfileModel(
                name = snapshot.getString("fullName") ?: snapshot.getString("name") ?: context.getString(R.string.user),
                nationalId = snapshot.getString("nationalId") ?: "",
                email = snapshot.getString("email") ?: "",
                phoneNumber = snapshot.getString("phone") ?: snapshot.getString("phoneNumber") ?: "",
                governorate = snapshot.getString("governorate")
            )
            userProfileDao.insertUserProfile(profile.toEntity())
            profile
        } catch (e: Exception) {
            userProfileDao.getUserProfile()?.toDomain()
        }
    }

    override suspend fun updateProfile(profile: UserProfileModel): Boolean {
        return try {
            val uid = auth.currentUser?.uid ?: return false
            
            userProfileDao.insertUserProfile(profile.toEntity())
            val updates = mapOf(
                "fullName" to profile.name,
                "email" to profile.email,
                "phone" to profile.phoneNumber,
                "nationalId" to profile.nationalId,
                "governorate" to profile.governorate
            )
            
            firestore.collection("users").document(uid).update(updates).await()
            true
        } catch (e: Exception) {
            try {
                val uid = auth.currentUser?.uid ?: return false
                val updates = mapOf(
                    "fullName" to profile.name,
                    "email" to profile.email,
                    "phone" to profile.phoneNumber,
                    "nationalId" to profile.nationalId,
                    "governorate" to profile.governorate
                )
                firestore.collection("users").document(uid).set(updates).await()
                true
            } catch (e2: Exception) {
                false
            }
        }
    }

    override suspend fun clearUserData() {
        userProfileDao.clearUserProfile()
    }
}