package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.model.OrderTrackingDetail
import com.example.yourdigitalpath.domain.repository.TrackingRepository
import com.example.yourdigitalpath.domain.usecase.order.CalculateOrderPercentageUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveOrderTrackingUseCase @Inject constructor(
    private val repository: TrackingRepository,
    private val calculateOrderPercentageUseCase: CalculateOrderPercentageUseCase
) {
    operator fun invoke(orderId: String): Flow<OrderTrackingDetail?> {
        return repository.observeOrderTracking(orderId).map { detail ->
            detail?.copy(
                progressPercent = calculateOrderPercentageUseCase(detail.steps)
            )
        }
    }
}