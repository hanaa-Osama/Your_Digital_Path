package com.example.yourdigitalpath.data.repositoryImp

import com.example.yourdigitalpath.data.local.Dao.OrderDao
import com.example.yourdigitalpath.data.mapper.toDbStatus
import com.example.yourdigitalpath.data.mapper.toDomain
import com.example.yourdigitalpath.domain.model.Order
import com.example.yourdigitalpath.domain.model.OrderStatus
import com.example.yourdigitalpath.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao
) : OrderRepository {

    override fun getAllOrders(): Flow<List<Order>> =
        orderDao.getAllOrders().map {
            it.map { entity -> entity.toDomain() }
        }

    override fun getOrderByStatus(status: OrderStatus): Flow<List<Order>> =
        orderDao.getOrdersByStatus(status.toDbStatus()).map {
            it.map { entity -> entity.toDomain() }
        }

    override suspend fun getOrderById(id: String): Order? =
        orderDao.getOrderById(id)?.toDomain()
}