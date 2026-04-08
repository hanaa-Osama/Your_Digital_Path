package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.model.UserProfile
import com.example.yourdigitalpath.domain.repository.ProfileRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): UserProfile {
        return repository.getUserProfile()
    }
}