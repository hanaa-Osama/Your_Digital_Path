package com.blqes.digi.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.blqes.digi.model.Order
import com.blqes.digi.model.OrderStatus
import com.blqes.digi.ui.components.OrderCard

@Composable
fun LastOrdersSection() {
    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "آخر الطلبات",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(12.dp))

            val orders = listOf(
                Order(
                    icon = Icons.Default.Person,
                    title = "شهادة ميلاد",
                    date = "20 مارس 2025",
                    status = OrderStatus.COMPLETED
                ),
                Order(
                    icon = Icons.Default.Notifications,
                    title = "تجديد هوية",
                    date = "2 أبريل 2025",
                    status = OrderStatus.IN_PROGRESS
                )
            )

            orders.forEach { order ->
                OrderCard(order)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
@Preview
private fun LastOrdersSectionPrev() {
    LastOrdersSection()
}