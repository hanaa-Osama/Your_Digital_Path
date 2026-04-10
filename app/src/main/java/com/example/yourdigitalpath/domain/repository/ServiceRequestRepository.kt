package com.example.yourdigitalpath.domain.repository

import com.example.yourdigitalpath.domain.model.ServiceRequestModel

interface ServiceRequestRepository {
    suspend fun saveServiceRequest(request: ServiceRequestModel)
    suspend fun getLastServiceRequest(): ServiceRequestModel?
}