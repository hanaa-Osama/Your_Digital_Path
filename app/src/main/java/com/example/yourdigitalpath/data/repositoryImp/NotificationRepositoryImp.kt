package com.example.yourdigitalpath.data.repositoryImp

import com.example.yourdigitalpath.data.dataSource.local.NotificationDao
import com.example.yourdigitalpath.data.dataSource.local.NotificationEntity
import com.example.yourdigitalpath.domain.model.NotificationItem
import com.example.yourdigitalpath.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotificationRepositoryImpl(
    private val dao: NotificationDao
) : NotificationRepository {

    override fun getAllNotifications(): Flow<List<NotificationItem>> {

        return dao.getNotificationsFlow().map { entities ->
            entities.map {
                NotificationItem(it.id, it.title, it.message, it.timeAgo, it.type, it.isRead)
            }
        }
    }

    override suspend fun markAsRead(id: String) {
        dao.markAsRead(id)
    }

    override suspend fun saveNotification(notification: NotificationItem) {
        val entity = NotificationEntity(
            id = notification.id,
            title = notification.title,
            message = notification.message,
            timeAgo = notification.timeAgo,
            type = notification.type,
            isRead = notification.isRead
        )
        dao.insert(entity)
    }
}