package com.example.yourdigitalpath.domain.model

data class ServiceRequestModel(
    val id: String = "",
    val selectedType: String = "",
    val requestReason: String = "",
    val otherReason: String? = "",
    val deliveryMethod: String = "",
    val copiesCount: Int = 1,
    val documentsUrls: List<String> = emptyList()
)