package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.model.AppSettingsModel
import com.example.yourdigitalpath.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetAppSettingsUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    operator fun invoke(): Flow<AppSettingsModel> {
        return combine(
            repository.getLanguage(),
            repository.getDisplayMode()
        ) { language, mode ->
            AppSettingsModel(language = language, displayMode = mode)
        }
    }
}