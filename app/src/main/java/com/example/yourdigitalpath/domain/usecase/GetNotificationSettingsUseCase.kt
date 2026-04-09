package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.model.NotificationSettingsModel
import com.example.yourdigitalpath.domain.repository.PreferencesRepository
import javax.inject.Inject

class GetNotificationSettingsUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    operator fun invoke(): NotificationSettingsModel {
        return NotificationSettingsModel(
            orderNotifications = repository.isOrderNotificationsEnabled(),
            offersNotifications = repository.isOffersNotificationsEnabled(),
            systemNotifications = repository.isSystemNotificationsEnabled()
        )
    }
}