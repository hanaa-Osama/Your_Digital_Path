package com.example.yourdigitalpath.presentation.Home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.HeartBroken
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.Color

data class Event(
    val title: String,
    val subtitle: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val price: String,
    val color: Color,
    val route: String
)

fun getEvents() = listOf(
    Event("شهادة الميلاد", "استخراج / تجديد", Icons.Outlined.Article,  "20 جنيه", Color(0xFFEBF0F7), "service_request_screen/شهادة الميلاد"),
    Event("بطاقة الهوية",  "إصدار / تجديد",   Icons.Outlined.Badge,    "35 جنيه", Color(0xFFEBF0F7), "service_request_screen/بطاقة الهوية"),
    Event("شهادة الزواج",  "رسمي / موثق للخارج", Icons.Outlined.Favorite, "30 جنيه", Color(0xFFEBF0F7), "service_request_screen/شهادة الزواج"),
    Event("شهادة الوفاة",  "إصدار / نسخ رسمية", Icons.Outlined.Person,  "20 جنيه", Color(0xFFEBF0F7), "service_request_screen/شهادة الوفاة"),
    Event("شهادة الطلاق",  "قضائي / مأذون",    Icons.Outlined.HeartBroken, "30 جنيه", Color(0xFFEBF0F7), "service_request_screen/شهادة الطلاق"),
)