package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.model.Order
import com.example.yourdigitalpath.domain.repository.OrderStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMyOrdersUseCase @Inject constructor(
    private val repository: OrderStatusRepository
) {
    operator fun invoke(): Flow<List<Order>> =
        repository.getAllOrders()
            .map { list -> list.sortedByDescending { it.requestDate } }
}