package com.example.yourdigitalpath.domain.model


data class TrackingStep(
    val id: Int,
    val title: String,
    val timestamp: String,
    val status: String // "completed", "current", "pending"
)

data class OrderTrackingDetail(
    val orderId: String,
    val serviceType: String,
    val date: String,
    val price: String,
    val steps: List<TrackingStep>
)

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val timeAgo: String,
    val type: String,
    val isRead: Boolean
)