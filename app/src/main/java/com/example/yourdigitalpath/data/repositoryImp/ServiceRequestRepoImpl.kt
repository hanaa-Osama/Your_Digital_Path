package com.example.yourdigitalpath.data.repositoryImp

import com.example.yourdigitalpath.data.dataSource.local.Dao.ServiceRequestDao
import com.example.yourdigitalpath.data.dataSource.local.Entity.ServiceRequestEntity
import com.example.yourdigitalpath.domain.model.ServiceRequestModel
import com.example.yourdigitalpath.domain.repository.ServiceRequestRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ServiceRequestRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val serviceRequestDao: ServiceRequestDao
) : ServiceRequestRepository {
    override suspend fun saveServiceRequest(request: ServiceRequestModel) {
        // Save to Room for caching
        serviceRequestDao.saveServiceRequest(
            ServiceRequestEntity(
                selectedType = request.selectedType,
                requestReason = request.requestReason,
                otherReason = request.otherReason,
                deliveryMethod = request.deliveryMethod,
                copiesCount = request.copiesCount
            )
        )

        // Save to Firebase
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

    override suspend fun getLastServiceRequest(): ServiceRequestModel? {
        return serviceRequestDao.getLastServiceRequest()?.let {
            ServiceRequestModel(
                selectedType = it.selectedType,
                requestReason = it.requestReason,
                otherReason = it.otherReason,
                deliveryMethod = it.deliveryMethod,
                copiesCount = it.copiesCount
            )
        }
    }
}