package com.example.yourdigitalpath.presentation.Register.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.presentation.Register.RegisterState
import com.example.yourdigitalpath.presentation.Register.RegisterViewModel
import com.example.yourdigitalpath.presentation.Register.components.*
import com.example.yourdigitalpath.ui.components.PrimaryBlue

@Composable
fun AccountDataScreen(
    onBack: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {},
    viewModel: RegisterViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirm by remember { mutableStateOf(false) }

    val state by viewModel.state.collectAsState()

    val isEmailValid = email.contains("@") && email.contains(".")
    val isPasswordValid = password.length >= 8
    val passwordsMatch = password == confirmPassword && confirmPassword.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBlue)
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            RegisterTopBar(onBack = onBack)
            RegisterStepsIndicator(currentStep = 2)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White, RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.End
        ) {
            RegisterSectionHeader(title = "بيانات الحساب")
            Spacer(modifier = Modifier.height(20.dp))

            RegisterInputField(
                label = "البريد الإلكتروني",
                value = email,
                onValueChange = { email = it },
                placeholder = "example@email.com",
                isVerified = isEmailValid
            )

            PasswordInputField(
                label = "كلمة المرور",
                value = password,
                onValueChange = { password = it },
                showPassword = showPassword,
                onToggleVisibility = { showPassword = !showPassword },
                isVerified = isPasswordValid,
                isError = password.isNotEmpty() && !isPasswordValid,
                errorMessage = "كلمة المرور يجب أن تكون 8 أحرف على الأقل"
            )

            PasswordInputField(
                label = "تأكيد كلمة المرور",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                showPassword = showConfirm,
                onToggleVisibility = { showConfirm = !showConfirm },
                isVerified = passwordsMatch,
                isError = confirmPassword.isNotEmpty() && !passwordsMatch,
                errorMessage = "كلمتا المرور غير متطابقتين"
            )

            Spacer(modifier = Modifier.height(16.dp))

            RegisterWarningCard(
                message = "احرص على اختيار كلمة مرور قوية تحتوي على أحرف وأرقام ورموز"
            )

            Spacer(modifier = Modifier.height(24.dp))

            RegisterButton(
                text = "إنشاء الحساب",
                onClick = { viewModel.register(email = email, password = password) },
                enabled = isEmailValid && isPasswordValid && passwordsMatch
            )

            Spacer(modifier = Modifier.height(20.dp))

            when (state) {
                is RegisterState.Loading -> { CircularProgressIndicator() }
                is RegisterState.Success -> {
                    LaunchedEffect(Unit) {
                        onRegisterSuccess()
                        viewModel.resetState()
                    }
                }
                is RegisterState.Error -> {
                    Text(text = (state as RegisterState.Error).message, color = Color.Red)
                }
                else -> {}
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAccountDataScreen() {
    AccountDataScreen()
}