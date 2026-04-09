package com.example.yourdigitalpath.domain.repository

import android.net.Uri
import com.example.yourdigitalpath.domain.model.ServiceRequestModel

interface ServiceRequestRepository {
    suspend fun saveServiceRequest(request: ServiceRequestModel)
    suspend fun uploadDocument(fileUri: Uri): String
}