package com.example.yourdigitalpath.data.repositoryImp

import com.example.yourdigitalpath.domain.model.OrderTrackingDetail
import com.example.yourdigitalpath.domain.repository.OrderTrackRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrderTrackRepositoryImp @Inject constructor(
    private val firestore: FirebaseFirestore
) : OrderTrackRepository {


    override suspend fun addNewOrder(order: OrderTrackingDetail) {
        try {
            firestore.collection("orders")
                .document(order.orderId)
                .set(order)
                .await()
        } catch (e: Exception) {
            throw e
        }
    }

}