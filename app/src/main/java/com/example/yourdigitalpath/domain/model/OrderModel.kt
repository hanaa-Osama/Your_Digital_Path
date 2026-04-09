package com.example.yourdigitalpath.domain.model

data class OrderModel(
    val id: String,
    val serviceName: String,
    val requestDate: Long,
    val status: OrderStatus,
    val totalFee: Int,
    val copiesCount: Int,
    val deliveryMethod: String,
    val progressPercent: Int,
)