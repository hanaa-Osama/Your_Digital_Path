package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.domain.model.OrderTrackingDetail
import com.example.yourdigitalpath.domain.model.TrackingStep
import com.example.yourdigitalpath.domain.repository.OrderTrackRepository
import com.example.yourdigitalpath.domain.repository.ServiceRequestRepository
import com.example.yourdigitalpath.domain.repository.certificates.CertificatesRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class SubmitFinalOrderUseCase @Inject constructor(

    private val birthRepo: CertificatesRepository,
    private val serviceRepo: ServiceRequestRepository,
    private val firebaseRepo: OrderTrackRepository
) {
    suspend operator fun invoke(): String {
        val certificate = birthRepo.getCachedBirthCertificate()
            ?: throw Exception("No certificate data found")

        val requestDetails = serviceRepo.getLastServiceRequest()
            ?: throw Exception("No request details found")

        val orderId = "REQ-${System.currentTimeMillis()}"

        val date = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        ).format(Date())

        val totalPrice =
            (requestDetails.copiesCount * 20).toString()

        val initialSteps = listOf(
            TrackingStep(
                id = 1,
                status = "completed",
                title = R.string.order_received,
                timestamp = "Now"
            ),
            TrackingStep(
                id = 2,
                status = "current",
                title = R.string.under_review,
                timestamp = "Checking data"
            ),
            TrackingStep(
                id = 3,
                status = "pending",
                title = R.string.document_processing,
                timestamp = ""
            ),
            TrackingStep(
                id = 4,
                status = "pending",
                title = R.string.shipped,
                timestamp = ""
            ),
            TrackingStep(
                id = 5,
                status = "pending",
                title = R.string.delivered,
                timestamp = ""
            )
        )

        val finalOrder = OrderTrackingDetail(
            orderId = orderId,
            serviceType = "Birth Certificate - ${requestDetails.selectedType}",
            date = date,
            price = totalPrice,
            deliveryMethod = requestDetails.deliveryMethod.ifEmpty {
                "Home Delivery"
            },
            steps = initialSteps
        )

        firebaseRepo.addNewOrder(finalOrder)
        return orderId
    }
}

//  لما هاله تعمل زرار تاكيد الطلب دا الحدث

//onClick = {
//    trackingViewModel.confirmAndSubmitOrder { newOrderId ->
//        navController.navigate("tracking_details/$newOrderId")
//    }
//}