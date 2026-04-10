package com.example.yourdigitalpath.domain.repository

import com.example.yourdigitalpath.domain.model.NotificationItem
import kotlinx.coroutines.flow.Flow


interface NotificationRepository {
    fun getAllNotifications(): Flow<List<NotificationItem>>
    suspend fun markAsRead(id: String)
    suspend fun saveNotification(notification: NotificationItem)
    suspend fun clearAllNotifications()
}