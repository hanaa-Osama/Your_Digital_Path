package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.repository.NotificationRepository

class MarkNotificationAsReadUseCase(private val repository: NotificationRepository) {
    suspend operator fun invoke(id: String) {
        repository.markAsRead(id)
    }
}