package com.blqes.digi.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
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
fun EventCard(event: Event, price: String, navController: NavHostController) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                navController.navigate("details_screen")

            }

    ) {

        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(12.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(45.dp)
                    .background(event.color, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(event.icon, contentDescription = null)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(event.title, fontWeight = FontWeight.Bold)

            Text(
                event.subtitle,
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .background(
                        Color(0xfFEBF0F7),
                        RoundedCornerShape(8.dp)
                    ) // لون الخلفية وشكل الحواف
                    .padding(horizontal = 8.dp, vertical = 4.dp) // padding جوا المربع
                    .align(Alignment.Start) // لو عايزة يكون على الشمال
            ) {
                Text(
                    text = price,
                    fontWeight = FontWeight.Bold,
                    color = Color(
                        0XFF3D5A80
                    ), // لون الخط
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
@Preview
private fun EventCardprev() {
    val sampleEvent = Event(
        title = "UI Design",
        subtitle = "3 tasks remaining",

        color = Color(0xFF2ED1C0),
        icon = Icons.Default.Star,
        price = "20egp",
        route = ""
    )

    EventCard(event = sampleEvent, price = "$25", navController = rememberNavController())
}