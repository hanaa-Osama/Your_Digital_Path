package com.example.yourdigitalpath.presentation.Register.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val WarningYellow = Color(0xFFFDF5E0)

@Composable
fun RegisterWarningCard(message: String) {
    Surface(
        color = WarningYellow,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = message,
                fontSize = 12.sp,
                color = Color(0xFF8A6A1F),
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Outlined.Info, contentDescription = null, tint = Color(0xFF8A6A1F), modifier = Modifier.size(18.dp))
        }
    }
}