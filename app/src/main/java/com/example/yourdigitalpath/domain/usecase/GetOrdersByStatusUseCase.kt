package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.model.Order
import com.example.yourdigitalpath.domain.model.OrderStatus
import com.example.yourdigitalpath.domain.repository.OrderStatusRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersByStatusUseCase @Inject constructor(
    private val repository: OrderStatusRepository
) {
    operator fun invoke(status: OrderStatus): Flow<List<Order>> =
        repository.getOrderByStatus(status)
}