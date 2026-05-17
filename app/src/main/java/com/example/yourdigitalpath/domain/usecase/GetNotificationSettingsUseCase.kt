package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.model.NotificationSettingsModel
import com.example.yourdigitalpath.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetNotificationSettingsUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    operator fun invoke(): Flow<NotificationSettingsModel> {
        return combine(
            repository.isOrderNotificationsEnabled(),
            repository.isOffersNotificationsEnabled(),
            repository.isSystemNotificationsEnabled()
        ) { order, offers, system ->
            NotificationSettingsModel(
                orderNotifications = order,
                offersNotifications = offers,
                systemNotifications = system
            )
        }
    }
}