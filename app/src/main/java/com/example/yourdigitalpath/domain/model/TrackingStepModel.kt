package com.example.yourdigitalpath.domain.model


data class TrackingStep(
    val id: Long = 0L,
    val title: String,
    val timestamp: String = "",
    val status: String = "pending",
    val description: String? = null
)

data class OrderTrackingDetail(
    val orderId: String = "",
    val userId: String = "",
    val serviceType: String = "",
    val date: String = "",
    val price: String = "",
    val deliveryMethod: String = "",
    val progressPercent: Int = 0,
    val steps: List<TrackingStep> = emptyList()
)
