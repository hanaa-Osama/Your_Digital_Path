package com.example.yourdigitalpath.data.repositoryImp

import android.content.Context
import com.example.yourdigitalpath.data.dataSource.local.Dao.NotificationDao
import com.example.yourdigitalpath.data.dataSource.local.Entity.NotificationEntity
import com.example.yourdigitalpath.data.mapper.toDomain
import com.example.yourdigitalpath.domain.model.NotificationItem
import com.example.yourdigitalpath.domain.repository.NotificationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val dao: NotificationDao,
    @ApplicationContext private val context: Context
) : NotificationRepository {

    override fun getAllNotifications(): Flow<List<NotificationItem>> {
        return dao.getNotificationsFlow().map { entities ->

            entities.map { it.toDomain(context) }
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
            type = notification.type,
            isRead = notification.isRead,
            createdAt = System.currentTimeMillis()
        )
        dao.insertNotification(entity)
    }

    override suspend fun clearAllNotifications() {
        dao.clearAll()
    }

    override suspend fun deleteNotification(id: String) {
        dao.deleteNotification(id)
    }
}