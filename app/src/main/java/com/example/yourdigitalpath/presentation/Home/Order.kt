package com.blqes.digi.model

import androidx.compose.ui.graphics.vector.ImageVector

enum class OrderStatus { COMPLETED, IN_PROGRESS }

data class Order(
    val icon: ImageVector,
    // val status: OrderStatus,
    val title: String,
    val date: String,
    val status: com.blqes.digi.model.OrderStatus,


    )