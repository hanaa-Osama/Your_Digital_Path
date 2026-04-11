package com.example.yourdigitalpath.domain.model

data class NotificationSettingsModel(
    val orderNotifications: Boolean,
    val offersNotifications: Boolean,
    val systemNotifications: Boolean
)