package com.example.yourdigitalpath.domain.usecase

import android.content.Context
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.domain.model.UserProfileModel
import com.example.yourdigitalpath.domain.repository.ProfileRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val repository: ProfileRepository,
    @ApplicationContext
    private val context: Context
) {
    suspend operator fun invoke(profile: UserProfileModel): Result<Unit> {
        return try {
            validateProfile(profile)

            val isSuccess = repository.updateProfile(profile)

            if (isSuccess) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(context.getString(R.string.database_update_failed)))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun validateProfile(profile: UserProfileModel) {
        val phoneRegex = Regex("^(010|011|012|015)\\d{8}$")

        when {
            profile.name.isBlank() -> {
                throw Exception(
                    context.getString(R.string.empty_name)
                )
            }
            profile.name.length < 3 -> {
                throw Exception(
                    context.getString(R.string.short_name)
                )
            }
            !phoneRegex.matches(profile.phoneNumber) -> {
                throw Exception(
                    context.getString(R.string.invalid_phone)
                )
            }
        }
    }
}