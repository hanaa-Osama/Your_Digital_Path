package com.example.yourdigitalpath.ui.theme

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatOrderDate(
    timestamp: Long
): String {

    val locale = Locale.getDefault()

    val formatter = SimpleDateFormat(
        "dd MMMM yyyy",
        locale
    )

    return formatter.format(Date(timestamp))
}