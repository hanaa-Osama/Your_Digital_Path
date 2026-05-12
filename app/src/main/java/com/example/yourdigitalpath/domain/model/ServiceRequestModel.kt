package com.example.yourdigitalpath.domain.model

data class ServiceRequestModel(
    val selectedType: String = "",
    val requestReason: String = "",
    val otherReason: String? = null,
    val deliveryMethod: String = "",
    val copiesCount: Int = 1,
    val totalFees: Double = 0.0,
    val secondPartyName: String = "",
    val secondPartyNationalId: String = "",
    val phoneNumber: String = "",
    val phoneError: String? = null,
    val nationalIdNumber: String = "",
    val nationalIdError: String? = null,
    val nationalIdUrls: List<String> = emptyList(),
    val serviceDocumentUrl: String? = null,
    val personalPhotoUrl: String? = null,
    val policeReportUrl: String? = null,
)