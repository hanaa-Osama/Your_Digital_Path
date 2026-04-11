package com.example.yourdigitalpath.presentation.profile.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.CompositionLocalProvider
import com.example.yourdigitalpath.presentation.profile.component.ActionButton
import com.example.yourdigitalpath.presentation.profile.component.PasswordField
import com.example.yourdigitalpath.presentation.profile.component.ProfileSimpleTopBar
import com.example.yourdigitalpath.ui.theme.AppColors

@Composable
fun SecurityScreen(
    onBackClick: () -> Unit
) {
    var currentPassword         by remember { mutableStateOf("") }
    var newPassword             by remember { mutableStateOf("") }
    var confirmPassword         by remember { mutableStateOf("") }
    var showCurrent             by remember { mutableStateOf(false) }
    var showNew                 by remember { mutableStateOf(false) }
    var showConfirm             by remember { mutableStateOf(false) }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                ProfileSimpleTopBar(
                    title = "الأمان والخصوصية",
                    onBackClick = onBackClick
                )
            },
            containerColor = AppColors.Background
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "تغيير كلمة المرور",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextPrimary,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                PasswordField(
                    label = "كلمة المرور الحالية",
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    isVisible = showCurrent,
                    onToggleVisibility = { showCurrent = !showCurrent }
                )

                Spacer(modifier = Modifier.height(16.dp))

                PasswordField(
                    label = "كلمة المرور الجديدة",
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    isVisible = showNew,
                    onToggleVisibility = { showNew = !showNew }
                )

                Spacer(modifier = Modifier.height(16.dp))

                PasswordField(
                    label = "تأكيد كلمة المرور",
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    isVisible = showConfirm,
                    onToggleVisibility = { showConfirm = !showConfirm }
                )

                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(16.dp))

                ActionButton(
                    text = "تحديث كلمة المرور",
                    onClick = onBackClick
                )
            }
        }
    }
}

@Preview(showBackground = true, locale = "ar")
@Composable
fun PreviewSecurityScreen() {
    SecurityScreen(onBackClick = {})
}