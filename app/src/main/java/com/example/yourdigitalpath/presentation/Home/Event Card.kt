package com.example.yourdigitalpath.presentation.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun EventCard(
    event: Event,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .border(0.5.dp, Color(0xFFE4E8ED), RoundedCornerShape(14.dp))
            .clickable { navController.navigate(event.route) }
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFEBF0F7), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = event.icon,
                contentDescription = null,
                tint = Color(0xFF3D5A80),
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = event.title,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color(0xFF1A1D23)
        )
        Text(
            text = event.subtitle,
            fontSize = 11.sp,
            color = Color(0xFF9BA3B2)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .background(Color(0xFFEBF0F7), RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            Text(
                text = event.price,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF3D5A80),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
@Preview
private fun EventCardPreview() {
    val sampleEvent = Event(
        title = "UI Design",
        subtitle = "3 tasks remaining",
        color = Color(0xFF2ED1C0),
        icon = Icons.Default.Star,
        price = "20egp",
        route = ""
    )
    EventCard(event = sampleEvent, navController = rememberNavController())
}