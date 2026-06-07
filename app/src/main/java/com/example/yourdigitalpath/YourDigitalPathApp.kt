package com.example.yourdigitalpath

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class YourDigitalPathApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // اضمن أن العربية هي الافتراضية لو مفيش لغة محفوظة
        val sharedPrefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        if (!sharedPrefs.contains("app_language")) {
            sharedPrefs.edit().putString("app_language", "ar").apply()
        }
        val language = sharedPrefs.getString("app_language", "ar") ?: "ar"
        LocaleManager.setLocale(this, language)

        createNotificationChannel()
        Firebase.firestore
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_name)
            val descriptionText = getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "digital_path_channel"
    }
}
