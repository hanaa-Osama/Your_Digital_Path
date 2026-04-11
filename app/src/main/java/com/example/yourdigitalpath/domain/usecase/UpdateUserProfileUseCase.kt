package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.model.UserProfileModel
import com.example.yourdigitalpath.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(profile: UserProfileModel): Result<Unit> {
        return try {
            validateProfile(profile)

            val isSuccess = repository.updateProfile(profile)

            if (isSuccess) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("فشل تحديث البيانات في قاعدة البيانات"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun validateProfile(profile: UserProfileModel) {
        val phoneRegex = Regex("^(010|011|012|015)\\d{8}$")

        when {
            profile.name.isBlank() -> throw Exception("الاسم لا يمكن أن يكون فارغاً")
            profile.name.length < 3 -> throw Exception("الاسم قصير جداً")

            !phoneRegex.matches(profile.phoneNumber) -> throw Exception("رقم الهاتف غير صحيح")
        }
    }
}