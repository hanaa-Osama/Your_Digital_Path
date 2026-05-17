package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.model.UserProfileModel
import com.example.yourdigitalpath.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): UserProfileModel? {
        return repository.getUserProfile()
    }

    fun invokeFlow(): Flow<UserProfileModel?> {
        return repository.getUserProfileFlow()
    }
}