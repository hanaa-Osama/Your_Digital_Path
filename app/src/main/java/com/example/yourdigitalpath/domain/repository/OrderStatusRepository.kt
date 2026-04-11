package com.example.yourdigitalpath.domain.repository

import com.example.yourdigitalpath.domain.model.OrderModel
import com.example.yourdigitalpath.domain.model.OrderStatus
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getAllOrders(): Flow<List<OrderModel>>
    fun getOrderByStatus(status: OrderStatus): Flow<List<OrderModel>>
    suspend fun getOrderById(id: String): OrderModel?
}