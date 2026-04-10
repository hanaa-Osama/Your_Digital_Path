package com.example.yourdigitalpath.presentation.Home

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Event(
    val title: String,
    val subtitle: String,
    val color: Color,
    val icon: ImageVector,
    val price: String,
    val route: String // نضيف هذا المتغير لتحديد وجهة كل كارد
)