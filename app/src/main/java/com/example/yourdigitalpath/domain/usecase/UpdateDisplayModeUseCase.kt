package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.repository.PreferencesRepository
import javax.inject.Inject
class UpdateDisplayModeUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    suspend operator fun invoke(mode: String) {
        repository.setDisplayMode(mode)
    }
}