package com.example.yourdigitalpath.data.repositoryImp

import android.net.Uri
import com.example.yourdigitalpath.data.dataSource.local.Dao.ServiceRequestDao
import com.example.yourdigitalpath.data.dataSource.local.Entity.ServiceRequestEntity
import com.example.yourdigitalpath.domain.model.ServiceRequestModel
import com.example.yourdigitalpath.domain.repository.ServiceRequestRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ServiceRequestRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val serviceRequestDao: ServiceRequestDao
) : ServiceRequestRepository {

    override suspend fun saveServiceRequest(request: ServiceRequestModel): String {
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

        // Save to Firebase in "orders" collection to match TrackingRepository
        val initialSteps = listOf(
            mapOf("status" to "PENDING", "title" to "تم استلام الطلب", "timestamp" to "الآن"),
            mapOf("status" to "PROCESSING", "title" to "جاري مراجعة البيانات", "timestamp" to ""),
            mapOf("status" to "COMPLETED", "title" to "تم التنفيذ", "timestamp" to "")
        )

        val data = hashMapOf(
            "selectedType" to request.selectedType,
            "serviceType" to request.selectedType,
            "requestReason" to request.requestReason,
            "otherReason" to request.otherReason,
            "deliveryMethod" to request.deliveryMethod,
            "copiesCount" to request.copiesCount,
            "price" to request.totalFees.toString(),
            "date" to "اليوم",
            "steps" to initialSteps,
            "timestamp" to com.google.firebase.Timestamp.now()
        )

        val result = firestore.collection("orders")
            .add(data)
            .await()
        return result.id
    }

    override suspend fun uploadDocument(fileUri: Uri): String {
        val fileName = "documents/doc_${System.currentTimeMillis()}.pdf"
        val ref = storage.reference.child(fileName)

        ref.putFile(fileUri).await()
        return ref.downloadUrl.await().toString()
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