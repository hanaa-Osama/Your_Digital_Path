package com.example.yourdigitalpath.domain.usecase

import android.net.Uri
import com.example.yourdigitalpath.domain.repository.ServiceRequestRepository
import javax.inject.Inject

class UploadDocumentUseCase @Inject constructor(
    private val repository: ServiceRequestRepository
) {
    suspend operator fun invoke(fileUri: Uri): String {
        return repository.uploadDocument(fileUri)
    }
}