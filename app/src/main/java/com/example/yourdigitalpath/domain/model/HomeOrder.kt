package com.example.yourdigitalpath.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class Order(
    val icon: ImageVector,
    val title: String,
    val date: String,
    val status: OrderStatus
)