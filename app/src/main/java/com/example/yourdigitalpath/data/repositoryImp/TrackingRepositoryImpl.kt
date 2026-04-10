package com.example.yourdigitalpath.data.repositoryImp

import android.util.Log
import com.example.yourdigitalpath.data.mapper.toDomain
import com.example.yourdigitalpath.data.model.TrackingFirebaseDto
import com.example.yourdigitalpath.domain.model.OrderTrackingDetail
import com.example.yourdigitalpath.domain.repository.TrackingRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class TrackingRepositoryImpl(
    private val firestore: FirebaseFirestore
) : TrackingRepository {

    override fun observeOrderTracking(orderId: String): Flow<OrderTrackingDetail?> = callbackFlow {

        val docRef = firestore.collection("orders").document(orderId)

        val listener = docRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                try {

                    val stepsList = snapshot.get("steps") as? List<Map<String, Any>> ?: emptyList()

                    val domainSteps = stepsList.mapIndexed { index, map ->
                        TrackingFirebaseDto(
                            status_code = map["status"] as? String
                                ?: "",
                            update_time = map["timestamp"] as? String
                                ?: "",
                            description = map["title"] as? String
                                ?: ""
                        ).toDomain(stepId = index.toLong())
                    }


                    val detail = OrderTrackingDetail(
                        orderId = snapshot.id,
                        steps = domainSteps,
                        serviceType = snapshot.getString("serviceType") ?: "خدمة غير معروفة",
                        date = snapshot.getString("date") ?: "",
                        price = snapshot.getString("price") ?: "0"
                    )

                    trySend(detail) // بنبعت البيانات للـ ViewModel
                } catch (e: Exception) {
                    Log.e("TrackingRepo", "Error parsing Firestore data: ${e.message}")
                    trySend(null)
                }
            } else {
                trySend(null)
            }
        }

        awaitClose { listener.remove() }
    }
}