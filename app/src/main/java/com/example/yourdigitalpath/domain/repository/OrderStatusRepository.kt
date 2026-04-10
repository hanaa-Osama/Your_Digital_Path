package com.example.yourdigitalpath.domain.repository

import com.example.yourdigitalpath.domain.model.Order
import com.example.yourdigitalpath.domain.model.OrderStatus
import com.example.yourdigitalpath.domain.model.OrderTrackingDetail
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getAllOrders(): Flow<List<OrderModel>>
    fun getOrderByStatus(status: OrderStatus): Flow<List<OrderModel>>
    suspend fun getOrderById(id: String): OrderModel?
}