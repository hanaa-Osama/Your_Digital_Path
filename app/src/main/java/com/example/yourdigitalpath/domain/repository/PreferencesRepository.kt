package com.example.yourdigitalpath.domain.repository

interface PreferencesRepository {
    fun isNotificationEnabled(): Boolean
    suspend fun setNotificationEnabled(isEnabled: Boolean)
    suspend fun clearSession()
}