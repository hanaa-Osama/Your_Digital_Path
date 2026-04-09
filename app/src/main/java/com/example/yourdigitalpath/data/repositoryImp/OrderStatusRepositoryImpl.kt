package com.example.yourdigitalpath.data.repositoryImp

import com.example.yourdigitalpath.data.local.Dao.OrderDao
import com.example.yourdigitalpath.data.mapper.toDbStatus
import com.example.yourdigitalpath.data.mapper.toDomain
import com.example.yourdigitalpath.domain.model.Order
import com.example.yourdigitalpath.domain.model.OrderStatus
import com.example.yourdigitalpath.domain.model.OrderTrackingDetail
import com.example.yourdigitalpath.domain.repository.OrderStatusRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrderStatusRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao,
    private val firestore: FirebaseFirestore
) : OrderStatusRepository {

    override fun getAllOrders(): Flow<List<Order>> =
        orderDao.getAllOrders().map {
            it.map { entity -> entity.toDomain() }
        }


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

    override fun getOrderByStatus(status: OrderStatus): Flow<List<Order>> =
        orderDao.getOrdersByStatus(status.toDbStatus()).map {
            it.map { entity -> entity.toDomain() }
        }

    override suspend fun getOrderById(id: String): Order? =
        orderDao.getOrderById(id)?.toDomain()

}