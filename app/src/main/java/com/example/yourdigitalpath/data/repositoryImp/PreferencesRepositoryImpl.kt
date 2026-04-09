package com.example.yourdigitalpath.data.repositoryImp

import android.content.SharedPreferences
import com.example.yourdigitalpath.domain.repository.PreferencesRepository
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val sharedPrefs: SharedPreferences
) : PreferencesRepository {

    companion object {
        private const val KEY_NOTIFICATIONS = "notifications_active"
        private const val KEY_AUTH_TOKEN = "auth_token"
    }

    override fun isNotificationEnabled(): Boolean {
        return sharedPrefs.getBoolean(KEY_NOTIFICATIONS, true)
    }

    override suspend fun setNotificationEnabled(isEnabled: Boolean) {
        sharedPrefs.edit().putBoolean(KEY_NOTIFICATIONS, isEnabled).apply()
    }

    override suspend fun clearSession() {
        sharedPrefs.edit().clear().apply()
    }
}