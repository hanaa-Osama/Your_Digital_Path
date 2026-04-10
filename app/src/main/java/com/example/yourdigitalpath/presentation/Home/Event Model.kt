package com.blqes.digi.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Event(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val price: String,
    val color: Color,
    val route: String // اسم الشاشة اللي هتروح لها
)

fun getEvents() = listOf(
    Event(
        "شهادة الميلاد",
        "ستخراج / تجديد",
        Icons.Default.Person,
        "20egp",
        Color(0xfFEBF0F7),
        "service_request_screen"
    ),
    Event(
        "شهادة الزواج",
        "رسمي / موثق للخارج",
        Icons.Default.Star,
        "20egp",
        Color(0xfFEBF0F7),"service_request_screen"


    ),
    Event(
        "بطاقة الهوية",
        "إصدار / تجديد",
        Icons.Default.Star,
        "20egp",
        Color(0xfFEBF0F7),"service_request_screen"
    ),
    Event(
        "شهادة الوفاة",
        "إصدار / نسخ رسمية",
        Icons.Default.Home,
        "20egp",
        Color(0xfFEBF0F7),"service_request_screen"
    ),
    Event(
        "شهادة الطلاق",
        "قضائي / مأذون",
        Icons.Default.Home,
        "20egp",
        Color(0xfFEBF0F7),"service_request_screen"
    )
)