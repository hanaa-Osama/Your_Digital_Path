package com.example.yourdigitalpath.presentation.Register.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.ui.components.PrimaryBlue

@Composable
fun RegisterSectionHeader(title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryBlue
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .width(3.dp)
                .height(20.dp)
                .background(PrimaryBlue, RoundedCornerShape(2.dp))
        )
    }
}