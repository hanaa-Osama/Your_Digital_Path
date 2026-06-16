package com.example.yourdigitalpath.data.repositoryImp

import android.content.Context
import android.net.Uri
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.data.dataSource.local.Dao.ServiceRequestDao
import com.example.yourdigitalpath.data.dataSource.local.Entity.ServiceRequestEntity
import com.example.yourdigitalpath.domain.model.ServiceRequestModel
import com.example.yourdigitalpath.domain.model.TrackingStep
import com.example.yourdigitalpath.domain.repository.ServiceRequestRepository
import com.example.yourdigitalpath.ui.theme.DateUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ServiceRequestRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val auth: FirebaseAuth,
    private val serviceRequestDao: ServiceRequestDao,
    @ApplicationContext private val context: Context
) : ServiceRequestRepository {

    override suspend fun saveServiceRequest(request: ServiceRequestModel): String {

        serviceRequestDao.saveServiceRequest(
            ServiceRequestEntity(
                selectedType = request.selectedType,
                requestReason = request.requestReason,
                otherReason = request.otherReason,
                deliveryMethod = request.deliveryMethod,
                copiesCount = request.copiesCount
            )
        )

        val initialSteps = listOf(
            TrackingStep(
                id = 1L,
                status = "completed",
                title = context.getString(R.string.order_received),
                timestamp = context.getString(R.string.now)
            ),
            TrackingStep(
                id = 2L,
                status = "current",
                title = context.getString(R.string.under_review),
                timestamp = context.getString(R.string.data_verification)
            ),
            TrackingStep(
                id = 3L,
                status = "pending",
                title = context.getString(R.string.document_processing),
                timestamp = ""
            ),
            TrackingStep(
                id = 4L,
                status = "pending",
                title = context.getString(R.string.shipped),
                timestamp = ""
            ),
            TrackingStep(
                id = 5L,
                status = "pending",
                title = context.getString(R.string.delivered),
                timestamp = ""
            )
        )

        val data = hashMapOf(
            "serviceName" to request.serviceName,
            "selectedType" to request.selectedType,
            "serviceType" to "${request.serviceName} - ${request.selectedType}",
            "requestReason" to request.requestReason,
            "otherReason" to request.otherReason,
            "deliveryMethod" to request.deliveryMethod,
            "copiesCount" to request.copiesCount,
            "price" to request.totalFees.toString(),
            "nationalIdNumber" to request.nationalIdNumber,
            "phoneNumber" to request.phoneNumber,
            "dataValues" to request.dataValues,
            "fileUrls" to request.fileUrls,
            "date" to DateUtils.formatOrderDate(System.currentTimeMillis()),
            "steps" to initialSteps,
            "status" to "InProgress",
            "progressPercent" to 45,
            "timestamp" to com.google.firebase.Timestamp.now(),
            "userId" to auth.currentUser?.uid
        )
        return try {
            val result = firestore
                .collection("orders")
                .add(data)
                .await()
            result.id
        } catch (e: Exception) {
            android.util.Log.e(
                "ServiceRequestRepo",
                "Error saving to Firestore: ${e.message}"
            )
            ""
        }
    }

    override suspend fun uploadDocument(fileUri: Uri): String {
        return try {
            val fileName =
                "documents/doc_${System.currentTimeMillis()}.pdf"
            val ref = storage.reference.child(fileName)
            ref.putFile(fileUri).await()
            ref.downloadUrl.await().toString()
        } catch (e: Exception) {
            android.util.Log.e(
                "ServiceRequestRepo",
                "Error uploading document: ${e.message}"
            )
            ""
        }
    }

    override suspend fun getLastServiceRequest(): ServiceRequestModel? {
        return serviceRequestDao
            .getLastServiceRequest()
            ?.let {
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
