package com.example.yourdigitalpath.domain.repository

import com.example.yourdigitalpath.domain.model.OrderDetails
import com.example.yourdigitalpath.domain.model.OrderStatus
import com.example.yourdigitalpath.domain.model.OrderTrackingDetail
import kotlinx.coroutines.flow.Flow

interface OrderStatusRepository {
    fun getAllOrders(): Flow<List<OrderDetails>>
    fun getOrderByStatus(status: OrderStatus): Flow<List<OrderDetails>>
    suspend fun getOrderById(id: String): OrderDetails?
    suspend fun addNewOrder(order: OrderTrackingDetail)
}