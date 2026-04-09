package com.example.yourdigitalpath.data.dataSource.remote

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.yourdigitalpath.MainActivity
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.YourDigitalPathApp
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

        showNotification(title, body)
    }

    private fun showNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, YourDigitalPathApp.CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher) // Make sure this icon exists
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }
}