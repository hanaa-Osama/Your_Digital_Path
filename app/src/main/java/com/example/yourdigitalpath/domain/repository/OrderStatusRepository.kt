package com.example.yourdigitalpath.domain.repository

import com.example.yourdigitalpath.domain.model.Order
import com.example.yourdigitalpath.domain.model.OrderStatus
import com.example.yourdigitalpath.domain.model.OrderTrackingDetail
import kotlinx.coroutines.flow.Flow

interface OrderStatusRepository {
    fun getAllOrders(): Flow<List<Order>>
    fun getOrderByStatus(status: OrderStatus): Flow<List<Order>>
    suspend fun getOrderById(id: String): Order?
    suspend fun addNewOrder(order: OrderTrackingDetail)
}