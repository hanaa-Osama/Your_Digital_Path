package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.model.ServiceRequestModel
import com.example.yourdigitalpath.domain.repository.ServiceRequestRepository
import javax.inject.Inject

class GetLastServiceRequestUseCase @Inject constructor(
    private val repository: ServiceRequestRepository
) {
    suspend operator fun invoke(): ServiceRequestModel? {
        return repository.getLastServiceRequest()
    }
}
