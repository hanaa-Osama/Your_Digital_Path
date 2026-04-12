package com.blqes.digi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blqes.digi.model.Order
import com.blqes.digi.model.OrderStatus

@Composable
fun OrderCard(order: Order) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .then(
                Modifier.padding(12.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFEBF0F7), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = order.icon,
                contentDescription = null,
                tint = Color(0xFF3D5A80),
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = order.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = Color(0xFF1A1D23)
            )
            Text(
                text = order.date,
                fontSize = 11.sp,
                color = Color(0xFF9BA3B2)
            )
        }

        Box(
            modifier = Modifier
                .background(
                    color = if (order.status == OrderStatus.COMPLETED) Color(0xFFEAF4EE)
                    else Color(0xFFFDF5E0),
                    shape = RoundedCornerShape(20.dp)
                )
                .border(1.dp, Color.Black.copy(0.1f), RoundedCornerShape(20.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                text = if (order.status == OrderStatus.COMPLETED) "مكتمل" else "جاري",
                color = if (order.status == OrderStatus.COMPLETED) Color(0xFF3A7D5A)
                else Color(0xFF8A6A1F),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}