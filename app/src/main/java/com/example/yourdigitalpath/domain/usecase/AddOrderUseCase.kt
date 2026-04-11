package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.model.OrderTrackingDetail
import com.example.yourdigitalpath.domain.model.TrackingStep
import com.example.yourdigitalpath.domain.repository.OrderRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class AddOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(serviceName: String) {

        val sdfDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val sdfTime = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val now = Date()

        val steps = listOf(
            TrackingStep(
                id = 1,
                status = "completed",
                title = "تم استلام الطلب",
                timestamp = sdfTime.format(now)
            ),
            TrackingStep(
                id = 2,
                status = "current",
                title = "قيد المراجعة",
                timestamp = "الآن"
            )
        )

        val dummyOrder = OrderTrackingDetail(
            orderId = "REQ-2025-00842",
            date = sdfDate.format(now),
            serviceType = serviceName,
            price = "100",
            steps = steps
        )

    }
}