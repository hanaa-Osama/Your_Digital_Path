package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.model.NotificationItem
import com.example.yourdigitalpath.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow

class GetNotificationsUseCase(
    private val repository: NotificationRepository
) {
    operator fun invoke(): Flow<List<NotificationItem>> {
        return repository.getAllNotifications()
    }
}