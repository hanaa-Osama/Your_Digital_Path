package com.example.yourdigitalpath.data.repositoryImp

import android.content.SharedPreferences
import com.example.yourdigitalpath.domain.repository.PreferencesRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    private fun <T> preferenceFlow(key: String, defaultValue: T): Flow<T> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, changedKey ->
            if (key == changedKey) {
                @Suppress("UNCHECKED_CAST")
                val value = when (defaultValue) {
                    is Boolean -> prefs.getBoolean(key, defaultValue) as T
                    is String -> (prefs.getString(key, defaultValue) ?: defaultValue) as T
                    else -> throw IllegalArgumentException("Unsupported type")
                }
                trySend(value)
            }
        }
        sharedPrefs.registerOnSharedPreferenceChangeListener(listener)
        
        @Suppress("UNCHECKED_CAST")
        val initialValue = when (defaultValue) {
            is Boolean -> sharedPrefs.getBoolean(key, defaultValue) as T
            is String -> (sharedPrefs.getString(key, defaultValue) ?: defaultValue) as T
            else -> throw IllegalArgumentException("Unsupported type")
        }
        trySend(initialValue)

        awaitClose {
            sharedPrefs.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    override fun isNotificationEnabled(): Flow<Boolean> = preferenceFlow(KEY_NOTIFICATIONS, true)

    override suspend fun setNotificationEnabled(isEnabled: Boolean) {
        sharedPrefs.edit().putBoolean(KEY_NOTIFICATIONS, isEnabled).apply()
    }

    override fun isOrderNotificationsEnabled(): Flow<Boolean> = preferenceFlow(KEY_ORDER_NOTIFICATIONS, true)

    override suspend fun setOrderNotificationsEnabled(isEnabled: Boolean) {
        sharedPrefs.edit().putBoolean(KEY_ORDER_NOTIFICATIONS, isEnabled).apply()
    }

    override fun isOffersNotificationsEnabled(): Flow<Boolean> = preferenceFlow(KEY_OFFERS_NOTIFICATIONS, false)

    override suspend fun setOffersNotificationsEnabled(isEnabled: Boolean) {
        sharedPrefs.edit().putBoolean(KEY_OFFERS_NOTIFICATIONS, isEnabled).apply()
    }

    override fun isSystemNotificationsEnabled(): Flow<Boolean> = preferenceFlow(KEY_SYSTEM_NOTIFICATIONS, true)

    override suspend fun setSystemNotificationsEnabled(isEnabled: Boolean) {
        sharedPrefs.edit().putBoolean(KEY_SYSTEM_NOTIFICATIONS, isEnabled).apply()
    }

    override fun getLanguage(): Flow<String> = preferenceFlow(KEY_LANGUAGE, "ar")

    override suspend fun setLanguage(language: String) {
        sharedPrefs.edit().putString(KEY_LANGUAGE, language).apply()
    }

    override fun getDisplayMode(): Flow<String> = preferenceFlow(KEY_DISPLAY_MODE, "light")

    override suspend fun setDisplayMode(mode: String) {
        sharedPrefs.edit().putString(KEY_DISPLAY_MODE, mode).apply()
    }

    override suspend fun clearSession() {
        val currentLang = sharedPrefs.getString(KEY_LANGUAGE, "ar") ?: "ar"
        val currentMode = sharedPrefs.getString(KEY_DISPLAY_MODE, "light") ?: "light"
        sharedPrefs.edit().clear().apply()
        setLanguage(currentLang)
        setDisplayMode(currentMode)
    }
}