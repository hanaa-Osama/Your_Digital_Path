package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.model.ServiceRequest
import com.example.yourdigitalpath.domain.repository.UploadRepository
import javax.inject.Inject

class SubmitRequestUseCase @Inject constructor(private val repository: UploadRepository) {
    suspend operator fun invoke(request: ServiceRequest) = repository.saveFinalRequest(request)
}