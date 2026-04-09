package com.example.yourdigitalpath.domain.repository

import android.net.Uri
import com.example.yourdigitalpath.domain.model.ServiceRequest

interface UploadRepository {
    suspend fun uploadFiles(uris: List<Uri>): Result<List<String>>
    suspend fun saveFinalRequest(request: ServiceRequest): Result<Unit>
}