package com.example.yourdigitalpath.data.repositoryImp
import android.net.Uri
import com.example.yourdigitalpath.domain.model.ServiceRequestModel
import com.example.yourdigitalpath.domain.repository.ServiceRequestRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
class ServiceRequestRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ServiceRequestRepository {

    override suspend fun saveServiceRequest(request: ServiceRequestModel) {
        firestore.collection("service_requests")
            .add(request)
            .await()
    }
    override suspend fun uploadDocument(fileUri: Uri): String {
        val fileName = "documents/doc_${System.currentTimeMillis()}.pdf"
        val ref = storage.reference.child(fileName)

        ref.putFile(fileUri).await()
        return ref.downloadUrl.await().toString()
    }
}