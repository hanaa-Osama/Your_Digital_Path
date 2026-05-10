package com.example.yourdigitalpath.data.repositoryImp

import android.net.Uri
import com.example.yourdigitalpath.data.dataSource.local.Dao.ServiceRequestDao
import com.example.yourdigitalpath.data.dataSource.local.Entity.ServiceRequestEntity
import com.example.yourdigitalpath.domain.model.ServiceRequestModel
import com.example.yourdigitalpath.domain.model.TrackingStep
import com.example.yourdigitalpath.domain.repository.ServiceRequestRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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

        // تم استخدام "completed" لتظهر خضراء وعليها علامة (صح)
        // وتم استخدام "current" لخطوة المراجعة لتظهر كخطوة نشطة
        val initialSteps = listOf(
            TrackingStep(
                id = 1,
                status = "completed",
                title = "تم استلام الطلب",
                timestamp = "الآن"
            ),
            TrackingStep(
                id = 2,
                status = "current",
                title = "قيد المراجعة",
                timestamp = "جاري التأكد من البيانات"
            ),
            TrackingStep(
                id = 3,
                status = "pending",
                title = "جاري استخراج الوثيقة",
                timestamp = ""
            ),
            TrackingStep(
                id = 4,
                status = "pending",
                title = "تم الشحن",
                timestamp = ""
            ),
            TrackingStep(
                id = 5,
                status = "pending",
                title = "تم التسليم",
                timestamp = ""
            )
        )

        val data = hashMapOf(
            "selectedType" to request.selectedType,
            "serviceType" to request.selectedType,
            "requestReason" to request.requestReason,
            "otherReason" to request.otherReason,
            "deliveryMethod" to request.deliveryMethod,
            "copiesCount" to request.copiesCount,
            "price" to request.totalFees.toString(),
            "date" to SimpleDateFormat("d MMMM yyyy", Locale("ar")).format(Date()),
            "steps" to initialSteps,
            "status" to "InProgress",
            "progressPercent" to 45, // النسبة المطلوبة 45%
            "timestamp" to com.google.firebase.Timestamp.now()
        )

        try {
            val result = firestore.collection("orders")
                .add(data)
                .await()
            return result.id
        } catch (e: Exception) {
            android.util.Log.e("ServiceRequestRepo", "Error saving to Firestore: ${e.message}")
            return ""
        }
    }

    override suspend fun uploadDocument(fileUri: Uri): String {
        try {
            val fileName = "documents/doc_${System.currentTimeMillis()}.pdf"
            val ref = storage.reference.child(fileName)

            ref.putFile(fileUri).await()
            return ref.downloadUrl.await().toString()
        } catch (e: Exception) {
            android.util.Log.e("ServiceRequestRepo", "Error uploading document: ${e.message}")
            return ""
        }
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