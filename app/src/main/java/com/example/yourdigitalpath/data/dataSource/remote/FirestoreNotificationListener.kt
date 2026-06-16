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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreNotificationListener @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val notificationDao: NotificationDao,
    private val context: Context
) {
    private var isFirstLoad = true
    private var listenerRegistration: ListenerRegistration? = null

    fun startListening() {
        val userId = auth.currentUser?.uid ?: return

        // Stop previous listener if any
        stopListening()

        listenerRegistration = firestore.collection("notifications")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    android.util.Log.e("FirestoreListener", "Listen failed.", error)
                    return@addSnapshotListener
                }

                if (isFirstLoad) {
                    isFirstLoad = false
                    return@addSnapshotListener
                }

                snapshots?.documentChanges?.forEach { dc ->
                    if (dc.type == DocumentChange.Type.ADDED) {
                        val id = dc.document.id
                        val title = dc.document.getString("title")
                            ?: context.getString(R.string.new_notification_title)
                        val message = dc.document.getString("message") ?: ""
                        val type = dc.document.getString("type") ?: "info"

                        val newNotification = NotificationEntity(
                            id = id,
                            userId = userId,
                            title = title,
                            message = message,
                            type = type,
                            isRead = false,
                            createdAt = System.currentTimeMillis()
                        )
                        CoroutineScope(Dispatchers.IO).launch {
                            notificationDao.insertNotification(newNotification)
                        }
                        showLocalNotification(newNotification.id, title, message)
                    }
                }
            }
    }

    private fun showLocalNotification(id: String, title: String, message: String) {
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
        notificationManager.notify(id.hashCode(), builder.build())
    }

    fun stopListening() {
        listenerRegistration?.remove()
        listenerRegistration = null
        isFirstLoad = true
    }
}
