package com.example.yourdigitalpath.data.repositoryImp

import android.content.SharedPreferences
import com.example.yourdigitalpath.domain.repository.PreferencesRepository
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val sharedPrefs: SharedPreferences
) : PreferencesRepository {

    companion object {
        private const val KEY_NOTIFICATIONS = "notifications_active"
        private const val KEY_ORDER_NOTIFICATIONS = "order_notifications"
        private const val KEY_OFFERS_NOTIFICATIONS = "offers_notifications"
        private const val KEY_SYSTEM_NOTIFICATIONS = "system_notifications"
        private const val KEY_LANGUAGE = "app_language"
        private const val KEY_DISPLAY_MODE = "display_mode"
        private const val KEY_AUTH_TOKEN = "auth_token"
    }

    override fun isNotificationEnabled(): Boolean {
        return sharedPrefs.getBoolean(KEY_NOTIFICATIONS, true)
    }

    override suspend fun setNotificationEnabled(isEnabled: Boolean) {
        sharedPrefs.edit().putBoolean(KEY_NOTIFICATIONS, isEnabled).apply()
    }

    override fun isOrderNotificationsEnabled(): Boolean {
        return sharedPrefs.getBoolean(KEY_ORDER_NOTIFICATIONS, true)
    }

    override suspend fun setOrderNotificationsEnabled(isEnabled: Boolean) {
        sharedPrefs.edit().putBoolean(KEY_ORDER_NOTIFICATIONS, isEnabled).apply()
    }

    override fun isOffersNotificationsEnabled(): Boolean {
        return sharedPrefs.getBoolean(KEY_OFFERS_NOTIFICATIONS, false)
    }

    override suspend fun setOffersNotificationsEnabled(isEnabled: Boolean) {
        sharedPrefs.edit().putBoolean(KEY_OFFERS_NOTIFICATIONS, isEnabled).apply()
    }

    override fun isSystemNotificationsEnabled(): Boolean {
        return sharedPrefs.getBoolean(KEY_SYSTEM_NOTIFICATIONS, true)
    }

    override suspend fun setSystemNotificationsEnabled(isEnabled: Boolean) {
        sharedPrefs.edit().putBoolean(KEY_SYSTEM_NOTIFICATIONS, isEnabled).apply()
    }

    override fun getLanguage(): String {
        return sharedPrefs.getString(KEY_LANGUAGE, "العربية") ?: "العربية"
    }

    override suspend fun setLanguage(language: String) {
        sharedPrefs.edit().putString(KEY_LANGUAGE, language).apply()
    }

    override fun getDisplayMode(): String {
        return sharedPrefs.getString(KEY_DISPLAY_MODE, "الوضع الفاتح") ?: "الوضع الفاتح"
    }

    override suspend fun setDisplayMode(mode: String) {
        sharedPrefs.edit().putString(KEY_DISPLAY_MODE, mode).apply()
    }

    override suspend fun clearSession() {
        sharedPrefs.edit().clear().apply()
    }
}