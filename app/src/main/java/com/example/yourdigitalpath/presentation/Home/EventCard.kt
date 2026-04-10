package com.example.yourdigitalpath.presentation.Home

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
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Card
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
import androidx.navigation.NavHostController

@Composable
fun EventCard(event: Event, price: String, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(12.dp))
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
            .clickable {
                // التعديل هنا: نستخدم المسار المخزن داخل الكائن event
                navController.navigate(event.route)
            }
    ) {
        // ... باقي الكود الخاص بالـ Column والـ Text كما هو بدون تغيير
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .background(
                        event.color,
                        androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(event.icon, contentDescription = null)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(event.title, fontWeight = FontWeight.Bold)
            Text(event.subtitle, fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .background(
                        Color(0xfFEBF0F7),
                        androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .align(Alignment.Start)
            ) {
                Text(
                    text = price,
                    fontWeight = FontWeight.Bold,
                    color = Color(0XFF3D5A80),
                    fontSize = 14.sp
                )
            }
        }
    }
}

val eventsList = listOf(
    Event(
        "بطاقة شخصية",
        "تجديد أو استخراج",
        Color.Blue,
        Icons.Default.Person,
        "50 EGP",
        "id_details_screen"
    ),
    Event(
        "شهادة ميلاد",
        "استخراج فوري",
        Color.Green,
        Icons.Default.ChildCare,
        "20 EGP",
        "birth_details_screen"
    ),
    Event("جواز سفر", "طلب جديد", Color.Red, Icons.Default.Public, "500 EGP", "passport_screen")
)