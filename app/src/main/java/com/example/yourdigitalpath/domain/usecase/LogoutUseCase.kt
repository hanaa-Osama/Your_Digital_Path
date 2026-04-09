package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.repository.PreferencesRepository
import com.example.yourdigitalpath.domain.repository.ProfileRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke() {
        profileRepository.clearUserData()
        preferencesRepository.clearSession()
    }
}