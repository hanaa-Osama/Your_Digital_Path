package com.blqes.digi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.blqes.digi.model.Order
import com.blqes.digi.model.OrderStatus

@Composable
fun OrderCard(order: Order) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF5F5F5))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 1️⃣ الأيقونة
        Icon(
            imageVector = order.icon,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // 2️⃣ الكلام (العنوان والتاريخ)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = order.title,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = order.date,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // 3️⃣ البوكس اللي فيه مكتمل أو جاري
        Box(
            modifier = Modifier
                .background(
                    color = if (order.status == OrderStatus.COMPLETED) Color(0xFFEAF4EE)
                    else Color(0xFFFDF5E0),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = if (order.status == OrderStatus.COMPLETED) "مكتمل" else "جاري",
                color = if (order.status == OrderStatus.COMPLETED) Color(0xFF4CAF50)
                else Color(0xFFFFC107),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}