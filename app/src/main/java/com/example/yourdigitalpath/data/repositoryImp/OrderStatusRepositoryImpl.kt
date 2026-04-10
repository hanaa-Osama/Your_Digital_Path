package com.example.yourdigitalpath.data.repositoryImp

import com.example.yourdigitalpath.data.local.Dao.OrderDao
import com.example.yourdigitalpath.data.mapper.toDbStatus
import com.example.yourdigitalpath.data.mapper.toDomain
import com.example.yourdigitalpath.domain.model.OrderDetails
import com.example.yourdigitalpath.domain.model.OrderStatus
import com.example.yourdigitalpath.domain.repository.OrderStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrderStatusRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao,
) : OrderStatusRepository {

    override fun getAllOrders(): Flow<List<OrderDetails>> =
        orderDao.getAllOrders().map {
            it.map { entity -> entity.toDomain() }
        }


    override fun getOrderByStatus(status: OrderStatus): Flow<List<OrderDetails>> =
        orderDao.getOrdersByStatus(status.toDbStatus()).map {
            it.map { entity -> entity.toDomain() }
        }

    override suspend fun getOrderById(id: String): OrderDetails? =
        orderDao.getOrderById(id)?.toDomain()

}