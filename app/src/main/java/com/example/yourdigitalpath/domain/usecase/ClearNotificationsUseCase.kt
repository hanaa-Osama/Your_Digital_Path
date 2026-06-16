package com.example.yourdigitalpath.domain.usecase


import com.example.yourdigitalpath.domain.repository.NotificationRepository
import javax.inject.Inject

class ClearNotificationsUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(userId: String) {
        repository.clearAllNotifications(userId)
    }
}