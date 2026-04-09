package com.example.yourdigitalpath.domain.model

data class ServiceRequest(
    val serviceName: String,
    val formData: Map<String, String>,
    val documentUrls: List<String> = emptyList(),
    val totalPrice: Double = 40.0
)
