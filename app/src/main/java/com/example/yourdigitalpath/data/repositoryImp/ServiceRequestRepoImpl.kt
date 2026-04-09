package com.example.yourdigitalpath.data.repositoryImp

import com.example.yourdigitalpath.domain.model.ServiceRequestModel
import com.example.yourdigitalpath.domain.repository.ServiceRequestRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ServiceRequestRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ServiceRequestRepository {
    override suspend fun saveServiceRequest(request: ServiceRequestModel) {
        val data = hashMapOf(
            "selectedType" to request.selectedType,
            "requestReason" to request.requestReason,
            "otherReason" to request.otherReason,
            "deliveryMethod" to request.deliveryMethod,
            "copiesCount" to request.copiesCount,
            "timestamp" to com.google.firebase.Timestamp.now()
        )
        firestore.collection("service_requests")
            .add(data)
            .await()
    }
}