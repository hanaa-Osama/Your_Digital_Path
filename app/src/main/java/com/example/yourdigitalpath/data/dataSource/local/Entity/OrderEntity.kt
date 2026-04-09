package com.example.yourdigitalpath.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val id: String,
    val serviceName: String,
    val requestDate: Long,
    val status: String,
    val rejectedReason: String? = null,
    val totalFee: Int,
    val copiesCount: Int,
    val deliveryMethod: String,
    val progressPercent: Int,
)
