package com.example.yourdigitalpath.presentation.order_track.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.yourdigitalpath.ui.theme.PrimaryBlue
import com.example.yourdigitalpath.ui.theme.SuccessGreen

@Composable
fun InfoRow(label: String, value: String, isAmount: Boolean = false) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            value,
            fontWeight = FontWeight.Medium,
            color = if (isAmount) SuccessGreen else PrimaryBlue
        )
        Text(label, color = Color.Gray)
    }
}