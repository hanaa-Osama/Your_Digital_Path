package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.model.AppSettingsModel
import com.example.yourdigitalpath.domain.repository.PreferencesRepository
import javax.inject.Inject

class GetAppSettingsUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    operator fun invoke(): AppSettingsModel {
        return AppSettingsModel(
            language = repository.getLanguage(),
            displayMode = repository.getDisplayMode()
        )
    }
}
