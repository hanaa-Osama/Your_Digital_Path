package com.example.yourdigitalpath.domain.model


data class TrackingStep(
    val id: Long = 0L, // التعديل الأهم: خليناها Long بدل Int
    val title: String = "",
    val timestamp: String = "",
    val status: String = "pending"
)

data class OrderTrackingDetail(
    val orderId: String = "",
    val serviceType: String = "",
    val date: String = "",
    val price: String = "",
    val deliveryMethod: String = "توصيل للمنزل",
    val steps: List<TrackingStep> = emptyList()
)
