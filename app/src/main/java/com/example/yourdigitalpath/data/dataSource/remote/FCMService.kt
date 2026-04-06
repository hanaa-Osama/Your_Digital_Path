package com.example.yourdigitalpath.data.dataSource.remote

import com.example.yourdigitalpath.data.dataSource.local.NotificationDao
import com.example.yourdigitalpath.data.dataSource.local.NotificationEntity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {
    @Inject
    lateinit var notificationDao: NotificationDao

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val title = message.notification?.title ?: message.data["title"] ?: "إشعار جديد"
        val body = message.notification?.body ?: message.data["body"] ?: ""
        val type = message.data["type"] ?: "info"

        val newNotification = NotificationEntity(
            id = UUID.randomUUID().toString(),
            title = title,
            message = body,
            type = type,
            isRead = false,
            createdAt = System.currentTimeMillis()
        )

        CoroutineScope(Dispatchers.IO).launch {
            notificationDao.insertNotification(newNotification)
        }
    }
}