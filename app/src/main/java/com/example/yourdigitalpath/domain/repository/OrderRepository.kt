package com.example.yourdigitalpath.domain.repository

import com.example.yourdigitalpath.domain.model.OrderTrackingDetail

interface OrderRepository {
    suspend fun addNewOrder(order: OrderTrackingDetail)
}