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
        // بنحدد الـ Document اللي هنراقبه بناءً على الـ orderId
        val docRef = firestore.collection("orders").document(orderId)

        val listener = docRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                try {
                    // 1. بنسحب لستة الخرائط (List of Maps) اللي شايلة الـ steps
                    val stepsList = snapshot.get("steps") as? List<Map<String, Any>> ?: emptyList()

                    // 2. بنحول كل Map لـ Domain Model (TrackingStep)
                    // خلي بالك: الأسماء هنا (status, title, timestamp) لازم تطابق اللي بتبعتيه في الـ UseCase
                    val domainSteps = stepsList.mapIndexed { index, map ->
                        TrackingFirebaseDto(
                            status_code = map["status"] as? String
                                ?: "", // غيرنا status_code لـ status عشان تطابق الـ UseCase
                            update_time = map["timestamp"] as? String
                                ?: "", // غيرنا update_time لـ timestamp
                            description = map["title"] as? String
                                ?: "" // غيرنا description لـ title
                        ).toDomain(stepId = index.toLong())
                    }

                    // 3. بنجمع الـ Object الكبير اللي الشاشة مستنياه
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
                trySend(null) // لو مفيش طلب بالـ ID ده
            }
        }

        // لو الـ Flow اتقفل (اليوزر خرج من الشاشة) بنوقف الـ Listener عشان ميسحبش نت وبطارية
        awaitClose { listener.remove() }
    }
}