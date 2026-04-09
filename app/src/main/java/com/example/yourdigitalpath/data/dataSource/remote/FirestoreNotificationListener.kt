package com.example.yourdigitalpath.data.dataSource.remote


import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.yourdigitalpath.MainActivity
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.YourDigitalPathApp
import com.example.yourdigitalpath.data.dataSource.local.Dao.NotificationDao
import com.example.yourdigitalpath.data.dataSource.local.Entity.NotificationEntity
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreNotificationListener @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val notificationDao: NotificationDao,
    private val context: Context
) {
    // المتغير ده عشان ميجيبش كل الإشعارات القديمة أول ما نفتح الأبلكيشن ويطلع بيها تنبيهات
    private var isFirstLoad = true

    fun startListening() {
        firestore.collection("notifications")
            .addSnapshotListener { snapshots, error ->
                if (error != null) return@addSnapshotListener

                if (isFirstLoad) {
                    isFirstLoad = false
                    return@addSnapshotListener
                }

                snapshots?.documentChanges?.forEach { dc ->
                    // لو في Document جديد اتضاف في الـ Firestore
                    if (dc.type == DocumentChange.Type.ADDED) {
                        val id = dc.document.id
                        val title = dc.document.getString("title") ?: "إشعار جديد"
                        val message = dc.document.getString("message") ?: ""
                        val type = dc.document.getString("type") ?: "info"

                        val newNotification = NotificationEntity(
                            id = id,
                            title = title,
                            message = message,
                            type = type,
                            isRead = false,
                            createdAt = System.currentTimeMillis()
                        )

                        // 1. نحفظه في الـ Room Database
                        CoroutineScope(Dispatchers.IO).launch {
                            notificationDao.insertNotification(newNotification)
                        }

                        // 2. نظهر التنبيه فوق في الموبايل
                        showLocalNotification(title, message)
                    }
                }
            }
    }

    private fun showLocalNotification(title: String, message: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, YourDigitalPathApp.CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }
}