package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.model.OrderTrackingDetail
import com.example.yourdigitalpath.domain.repository.TrackingRepository
import kotlinx.coroutines.flow.Flow

class ObserveOrderTrackingUseCase(
    private val repository: TrackingRepository
) {
    operator fun invoke(orderId: String): Flow<OrderTrackingDetail?> {
        return repository.observeOrderTracking(orderId)
    }
}