package com.example.yourdigitalpath.domain.repository

interface PreferencesRepository {
    fun isNotificationEnabled(): Boolean
    suspend fun setNotificationEnabled(isEnabled: Boolean)

    fun isOrderNotificationsEnabled(): Boolean
    suspend fun setOrderNotificationsEnabled(isEnabled: Boolean)

    fun isOffersNotificationsEnabled(): Boolean
    suspend fun setOffersNotificationsEnabled(isEnabled: Boolean)

    fun isSystemNotificationsEnabled(): Boolean
    suspend fun setSystemNotificationsEnabled(isEnabled: Boolean)

    fun getLanguage(): String
    suspend fun setLanguage(language: String)

    fun getDisplayMode(): String
    suspend fun setDisplayMode(mode: String)

    suspend fun clearSession()
}