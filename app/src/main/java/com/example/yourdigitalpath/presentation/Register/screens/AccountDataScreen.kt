package com.example.yourdigitalpath.presentation.Register.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.presentation.Register.RegisterState
import com.example.yourdigitalpath.presentation.Register.RegisterViewModel
import com.example.yourdigitalpath.presentation.Register.components.*
import com.example.yourdigitalpath.ui.theme.AppColors

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
            .background(AppColors.Primary)
    ) {
            RegisterTopBar(onBack = onBack)
            RegisterStepsIndicator(currentStep = 2)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(AppColors.Surface, RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                RegisterSectionHeader(
                    title = stringResource(R.string.account_data)
                )

                Spacer(modifier = Modifier.height(20.dp))

                RegisterInputField(
                    label = stringResource(R.string.email),
                    value = email,
                    onValueChange = { email = it },
                    placeholder = stringResource(R.string.email_placeholder),
                    isVerified = isEmailValid
                )

                PasswordInputField(
                    label = stringResource(R.string.password),
                    value = password,
                    onValueChange = { password = it },
                    showPassword = showPassword,
                    onToggleVisibility = { showPassword = !showPassword },
                    isVerified = isPasswordValid,
                    isError = password.isNotEmpty() && !isPasswordValid,
                    errorMessage = stringResource(R.string.password_error)
                )

                PasswordInputField(
                    label = stringResource(R.string.confirm_password),
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    showPassword = showConfirm,
                    onToggleVisibility = { showConfirm = !showConfirm },
                    isVerified = passwordsMatch,
                    isError = confirmPassword.isNotEmpty() && !passwordsMatch,
                    errorMessage = stringResource(R.string.passwords_not_match)
                )

                Spacer(modifier = Modifier.height(16.dp))

                RegisterWarningCard(
                    message = stringResource(R.string.password_warning)
                )

                Spacer(modifier = Modifier.height(24.dp))

                RegisterButton(
                    text = stringResource(R.string.create_account),
                    onClick = {
                        viewModel.register(
                            email = email,
                            password = password
                        )
                    },
                    enabled = isEmailValid && isPasswordValid && passwordsMatch
                )

                Spacer(modifier = Modifier.height(20.dp))

                when (state) {

                    is RegisterState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is RegisterState.Success -> {
                        LaunchedEffect(Unit) {
                            onRegisterSuccess()
                            viewModel.resetState()
                        }
                    }

                    is RegisterState.Error -> {
                        Text(
                            text = stringResource(
                                (state as RegisterState.Error).messageRes
                            ),
                            color = AppColors.Danger
                        )
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