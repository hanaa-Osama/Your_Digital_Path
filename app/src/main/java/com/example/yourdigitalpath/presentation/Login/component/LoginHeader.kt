package com.example.yourdigitalpath.presentation.Login.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.ui.theme.AppColors

@Composable
fun LoginHeader() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "مرحباً بك",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.TextPrimary,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "سجل دخولك للمتابعة",
                fontSize = 16.sp,
                color = AppColors.TextSecond,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
