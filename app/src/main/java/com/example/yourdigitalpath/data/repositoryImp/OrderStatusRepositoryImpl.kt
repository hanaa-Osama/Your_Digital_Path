package com.example.yourdigitalpath.data.repositoryImp

import android.content.Context
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.data.local.Dao.OrderDao
import com.example.yourdigitalpath.data.mapper.toDbStatus
import com.example.yourdigitalpath.data.mapper.toDomain
import com.example.yourdigitalpath.domain.model.OrderModel
import com.example.yourdigitalpath.domain.model.OrderStatus
import com.example.yourdigitalpath.domain.repository.OrderRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao,
    private val firestore: FirebaseFirestore,
    @ApplicationContext private val context: Context
) : OrderRepository {

    override fun getAllOrders(): Flow<List<OrderModel>> {
        val localOrders = orderDao.getAllOrders().map { entities ->
            entities.map { entity -> entity.toDomain(context) }
        }

        val remoteOrders = callbackFlow {
            val listener = firestore.collection("orders")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        android.util.Log.e("OrderRepo", "Listen failed.", error)
                        trySend(emptyList())
                        return@addSnapshotListener
                    }

                    val orders = snapshot?.documents?.mapNotNull { doc ->
                        val steps = doc.get("steps") as? List<Map<String, Any>> ?: emptyList()
                        val isCompleted = steps.all { it["status"] == "completed" }

                        val remoteServiceName = doc.getString("serviceName")
                        val serviceType = doc.getString("serviceType") ?: ""
                        val selectedType = doc.getString("selectedType") ?: ""

                        val finalServiceName = when {
                            !remoteServiceName.isNullOrEmpty() && selectedType.isNotEmpty() ->
                                "$remoteServiceName - $selectedType"
                            serviceType.isNotEmpty() && selectedType.isNotEmpty() ->
                                "$serviceType - $selectedType"
                            else -> serviceType.ifEmpty { context.getString(R.string.service) }
                        }

                        val requestTimestamp = doc.getTimestamp("timestamp")?.toDate()?.time
                            ?: doc.getString("date")?.let { dateStr ->
                                try {
                                    SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).parse(dateStr)?.time
                                } catch (e: Exception) { null }
                            } ?: System.currentTimeMillis()

                        OrderModel(
                            id = doc.id,
                            serviceName = finalServiceName,
                            requestDate = requestTimestamp,
                            status = if (isCompleted) OrderStatus.Completed else OrderStatus.InProgress,
                            totalFee = doc.getString("price")?.toIntOrNull() ?: 0,
                            copiesCount = 1,
                            deliveryMethod = doc.getString("deliveryMethod")
                                ?: context.getString(R.string.delivery),
                            progressPercent = if (steps.isEmpty()) 0 else {
                                val completedSteps = steps.count { it["status"] == "completed" }
                                (completedSteps * 100) / steps.size
                            }
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
        orderDao.getOrdersByStatus(status.toDbStatus()).map { entities ->
            entities.map { entity -> entity.toDomain(context) }
        }

    override suspend fun getOrderById(id: String): OrderModel? =
        orderDao.getOrderById(id)?.toDomain(context)
}