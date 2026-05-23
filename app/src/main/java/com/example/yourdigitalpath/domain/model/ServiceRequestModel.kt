package com.example.yourdigitalpath.domain.model

data class ServiceRequestModel(
    val id: String = "",
    val serviceName: String = "",
    val selectedType: String = "",
    val requestReason: String = "",
    val otherReason: String? = null,
    val deliveryMethod: String = "",
    val copiesCount: Int = 1,
    val totalFees: Double = 0.0,

    // Dynamic Form Data (The source of truth for Step 2 and 3)
    val dataValues: Map<String, String> = emptyMap(),
    val dataErrors: Map<String, String?> = emptyMap(),
    val fileUrls: Map<String, List<String>> = emptyMap(),

    // Core identification fields (Commonly used across all services for indexing/search)
    val nationalIdNumber: String = "",
    val phoneNumber: String = ""
)
