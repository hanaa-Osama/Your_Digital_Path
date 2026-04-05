package com.example.yourdigitalpath.data.repositoryImp

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
                val data = snapshot.toObject(OrderTrackingDetail::class.java)
                trySend(data)
            }
        }
        awaitClose { listener.remove() }
    }
}