package com.example.yourdigitalpath.presentation.notification.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.presentation.notification.NotificationItemData


@Composable
fun NotificationCard(item: NotificationItemData) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                Text(
                    item.title,
                    fontWeight = FontWeight.Bold,
                    color = item.color,
                    textAlign = TextAlign.Right
                )
                Text(
                    item.desc,
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(item.time, fontSize = 10.sp, color = Color.LightGray)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Surface(
                color = item.color.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    item.icon,
                    contentDescription = null,
                    tint = item.color,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}
