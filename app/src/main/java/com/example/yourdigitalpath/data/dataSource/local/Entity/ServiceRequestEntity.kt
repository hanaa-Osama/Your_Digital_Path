package com.example.yourdigitalpath.data.dataSource.local.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "last_service_request")
data class ServiceRequestEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0, // We only want to store the last one, so we can use a constant ID
    val selectedType: String,
    val requestReason: String,
    val otherReason: String?,
    val deliveryMethod: String,
    val copiesCount: Int
)
