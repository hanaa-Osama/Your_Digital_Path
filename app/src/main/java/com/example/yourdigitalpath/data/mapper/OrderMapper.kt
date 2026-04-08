package com.example.yourdigitalpath.data.mapper

import com.example.yourdigitalpath.data.local.entity.OrderEntity
import com.example.yourdigitalpath.domain.model.Order
import com.example.yourdigitalpath.domain.model.OrderStatus

fun String.toOrderStatus(rejectReason: String? = null): OrderStatus{
    return when(this){
        "Pending" -> OrderStatus.Pending
        "InProgress" -> OrderStatus.InProgress
        "Completed" -> OrderStatus.Completed
        "Issued" -> OrderStatus.Issued
        "Rejected" -> OrderStatus.Rejected(rejectReason?: "سبب غير محدد")
        else -> OrderStatus.Pending
    }
}

fun OrderStatus.toDbStatus(): String{
    return when(this){
        is OrderStatus.Pending -> "Pending"
        is OrderStatus.InProgress -> "InProgress"
        is OrderStatus.Completed -> "Completed"
        is OrderStatus.Issued -> "Issued"
        is OrderStatus.Rejected -> "Rejected"
    }
}

fun OrderEntity.toDomain(): Order{
    return Order(
        id = id,
        serviceName = serviceName,
        requestDate = requestDate,
        status = status.toOrderStatus(rejectedReason),
        totalFee = totalFee,
        copiesCount = copiesCount,
        deliveryMethod = deliveryMethod,
        progressPercent = progressPercent
    )
}

fun Order.toEntity(): OrderEntity{
    return OrderEntity(
        id = id,
        serviceName = serviceName,
        requestDate = requestDate,
        status = status.toDbStatus(),
        rejectedReason = if (status is OrderStatus.Rejected) status.reason else null,
        totalFee = totalFee,
        copiesCount = copiesCount,
        deliveryMethod = deliveryMethod,
        progressPercent = progressPercent
    )
}