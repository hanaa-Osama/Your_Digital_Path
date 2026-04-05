package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.model.NotificationItem
import com.example.yourdigitalpath.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow

class GetNotificationsUseCase {
    operator fun invoke(repository: NotificationRepository): Flow<List<NotificationItem>> {
        return repository.getAllNotifications()
    }
}