package com.example.yourdigitalpath.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun isNotificationEnabled(): Flow<Boolean>
    suspend fun setNotificationEnabled(isEnabled: Boolean)

    fun isOrderNotificationsEnabled(): Flow<Boolean>
    suspend fun setOrderNotificationsEnabled(isEnabled: Boolean)

    fun isOffersNotificationsEnabled(): Flow<Boolean>
    suspend fun setOffersNotificationsEnabled(isEnabled: Boolean)

    fun isSystemNotificationsEnabled(): Flow<Boolean>
    suspend fun setSystemNotificationsEnabled(isEnabled: Boolean)

    fun getLanguage(): Flow<String>
    suspend fun setLanguage(language: String)

    fun getDisplayMode(): Flow<String>
    suspend fun setDisplayMode(mode: String)

    suspend fun clearSession()
}