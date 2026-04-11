package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.repository.PreferencesRepository
import javax.inject.Inject

class ToggleOrderNotificationsUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    suspend operator fun invoke(isEnabled: Boolean) {
        repository.setOrderNotificationsEnabled(isEnabled)
    }
}

class ToggleOffersNotificationsUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    suspend operator fun invoke(isEnabled: Boolean) {
        repository.setOffersNotificationsEnabled(isEnabled)
    }
}

class ToggleSystemNotificationsUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    suspend operator fun invoke(isEnabled: Boolean) {
        repository.setSystemNotificationsEnabled(isEnabled)
    }
}