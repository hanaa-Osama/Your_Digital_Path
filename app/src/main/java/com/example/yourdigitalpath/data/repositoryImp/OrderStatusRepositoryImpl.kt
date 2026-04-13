package com.example.yourdigitalpath.data.repositoryImp

import com.example.yourdigitalpath.data.local.Dao.OrderDao
import com.example.yourdigitalpath.data.mapper.toDbStatus
import com.example.yourdigitalpath.data.mapper.toDomain
import com.example.yourdigitalpath.domain.model.OrderModel
import com.example.yourdigitalpath.domain.model.OrderStatus
import com.example.yourdigitalpath.domain.repository.OrderRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao,
    private val firestore: FirebaseFirestore
) : OrderRepository {

    override fun getAllOrders(): Flow<List<OrderModel>> {
        val localOrders = orderDao.getAllOrders().map { it.map { entity -> entity.toDomain() } }

        val remoteOrders = callbackFlow {
            val listener = firestore.collection("orders")
                .addSnapshotListener { snapshot, _ ->
                    val orders = snapshot?.documents?.mapNotNull { doc ->
                        val steps = doc.get("steps") as? List<Map<String, Any>> ?: emptyList()
                        val isCompleted = steps.all { it["status"] == "completed" }

                        OrderModel(
                            id = doc.id,
                            serviceName = doc.getString("serviceType") ?: "خدمة",
                            requestDate = System.currentTimeMillis(), // Default for simplicity
                            status = if (isCompleted) OrderStatus.Completed else OrderStatus.InProgress,
                            totalFee = doc.getString("price")?.toIntOrNull() ?: 0,
                            copiesCount = 1,
                            deliveryMethod = doc.getString("deliveryMethod") ?: "توصيل",
                            progressPercent = if (isCompleted) 100 else 45
                        )
                    } ?: emptyList()
                    trySend(orders)
                }
            awaitClose { listener.remove() }
        }

        return combine(localOrders, remoteOrders) { local, remote ->
            (local + remote).sortedByDescending { it.id }
        }
    }

    override fun getOrderByStatus(status: OrderStatus): Flow<List<OrderModel>> =
        orderDao.getOrdersByStatus(status.toDbStatus()).map {
            it.map { entity -> entity.toDomain() }
        }

    override suspend fun getOrderById(id: String): OrderModel? =
        orderDao.getOrderById(id)?.toDomain()

}