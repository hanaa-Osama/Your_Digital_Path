package com.example.yourdigitalpath.presentation.Register.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import com.example.yourdigitalpath.R

@Composable
fun RegisterStepsIndicator(currentStep: Int) {
    val steps = listOf(
        stringResource(R.string.personal_information),
        stringResource(R.string.account_information)
    )
    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)) {
        steps.forEachIndexed { index, label ->
            val isActive = index + 1 == currentStep
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = label,
                    color = if (isActive) Color.White else Color.White.copy(alpha = 0.6f),
                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (isActive) 3.dp else 1.dp)
                        .background(
                            if (isActive) Color.White else Color.White.copy(alpha = 0.3f)
                        )
                )
            }
        }
    }
}