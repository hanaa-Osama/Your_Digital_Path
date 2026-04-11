package com.example.yourdigitalpath.domain.usecase

import com.example.yourdigitalpath.domain.model.OrderModel
import com.example.yourdigitalpath.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMyOrdersUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    operator fun invoke(): Flow<List<OrderModel>> =
        repository.getAllOrders()
            .map { list -> list.sortedByDescending { it.requestDate } }
}