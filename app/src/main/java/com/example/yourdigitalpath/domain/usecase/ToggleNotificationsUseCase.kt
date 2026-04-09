package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.repository.PreferencesRepository
import javax.inject.Inject

class ToggleNotificationsUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    suspend operator fun invoke(isEnabled: Boolean) {
        repository.setNotificationEnabled(isEnabled)
    }
}