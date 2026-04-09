package com.example.yourdigitalpath.data.repositoryImp

import android.net.Uri
import com.example.yourdigitalpath.domain.model.ServiceRequest
import com.example.yourdigitalpath.domain.repository.UploadRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UploadRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val firestore: FirebaseFirestore
) : UploadRepository {

    override suspend fun uploadFiles(uris: List<Uri>): Result<List<String>> = try {
        val downloadUrls = uris.map { uri ->
            val fileName = "documents/${System.currentTimeMillis()}_${uri.lastPathSegment}"
            val ref = storage.reference.child(fileName)
            ref.putFile(uri).await()
            ref.downloadUrl.await().toString()
        }
        Result.success(downloadUrls)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun saveFinalRequest(request: ServiceRequest): Result<Unit> = try {
        firestore.collection("all_requests").add(request).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}