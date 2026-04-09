package com.example.yourdigitalpath.domain.model

data class ServiceRequestModel(
    val selectedType: String = "",
    val requestReason: String = "",
    val otherReason: String? = "",
    val deliveryMethod: String = "",
    val copiesCount: Int = 1
)