package com.example.yourdigitalpath.domain.model


data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val timeAgo: String,
    val type: String,
    val isRead: Boolean
)