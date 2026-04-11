package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.repository.PreferencesRepository
import javax.inject.Inject

class UpdateLanguageUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    suspend operator fun invoke(language: String) {
        repository.setLanguage(language)
    }
}