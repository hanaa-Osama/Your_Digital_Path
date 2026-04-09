package com.example.yourdigitalpath.domain.repository


import com.example.yourdigitalpath.domain.model.OrderTrackingDetail
import kotlinx.coroutines.flow.Flow

interface TrackingRepository {
    fun observeOrderTracking(orderId: String): Flow<OrderTrackingDetail?>
}
