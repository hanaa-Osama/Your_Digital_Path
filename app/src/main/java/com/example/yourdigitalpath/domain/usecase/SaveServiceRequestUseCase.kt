package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.model.ServiceRequestModel
import com.example.yourdigitalpath.domain.repository.ServiceRequestRepository
import javax.inject.Inject

class SaveServiceRequestUseCase @Inject constructor(
    private val repository: ServiceRequestRepository
) {
    suspend operator fun invoke(request: ServiceRequestModel): String {
        return repository.saveServiceRequest(request)
    }
}