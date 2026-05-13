package com.example.yourdigitalpath.presentation.profile.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.presentation.profile.component.ActionButton
import com.example.yourdigitalpath.presentation.profile.component.PasswordField
import com.example.yourdigitalpath.presentation.profile.component.ProfileSimpleTopBar
import com.example.yourdigitalpath.ui.theme.AppColors
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.presentation.viewModel.ProfileViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.let

@Composable
fun SecurityScreen(
    onBackClick: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showCurrent by remember { mutableStateOf(false) }
    var showNew by remember { mutableStateOf(false) }
    var showConfirm by remember { mutableStateOf(false) }

    val updateResult by viewModel.updateResult.collectAsState()

    val isPasswordValid = newPassword.length >= 8
    val passwordsMatch =
        newPassword == confirmPassword && confirmPassword.isNotEmpty()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val successMessage = stringResource(R.string.password_update_success)
    val failureMessage = stringResource(R.string.password_update_failed)

    LaunchedEffect(updateResult) {
        updateResult?.let {
            val message = if (it.isSuccess) {
                successMessage
            } else {
                failureMessage
            }
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
            }
            if (it.isSuccess) {
                onBackClick()
            }
            viewModel.resetUpdateResult()
        }
    }


    Scaffold(
        topBar = {
            ProfileSimpleTopBar(
                title = stringResource(R.string.security_and_privacy),
                onBackClick = onBackClick
            )
        },
        containerColor = AppColors.Background,
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(
                    horizontal = 20.dp,
                    vertical = 16.dp
                )
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.change_password),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.TextPrimary,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            PasswordField(
                label = stringResource(R.string.current_password),
                value = currentPassword,
                onValueChange = {
                    currentPassword = it
                },
                isVisible = showCurrent,
                onToggleVisibility = {
                    showCurrent = !showCurrent
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordField(
                label = stringResource(R.string.new_password),
                value = newPassword,
                onValueChange = {
                    newPassword = it
                },
                isVisible = showNew,
                onToggleVisibility = {
                    showNew = !showNew
                },
                isError = newPassword.isNotEmpty() && !isPasswordValid,
                errorMessage = stringResource(R.string.password_min_length)
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordField(
                label = stringResource(R.string.confirm_password),
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                },
                isVisible = showConfirm,
                onToggleVisibility = {
                    showConfirm = !showConfirm
                },
                isError = confirmPassword.isNotEmpty() && !passwordsMatch,
                errorMessage = stringResource(R.string.password_not_match)
            )

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(16.dp))

            ActionButton(
                text = stringResource(R.string.update_password),
                onClick = {
                    viewModel.updatePassword(
                        currentPassword = currentPassword,
                        newPassword = newPassword
                    )
                },
                enabled = currentPassword.isNotEmpty() &&
                        isPasswordValid &&
                        passwordsMatch
            )
        }
    }
}

@Preview(showBackground = true, locale = "ar")
@Composable
fun PreviewSecurityScreen() {

    SecurityScreen(
        onBackClick = {}
    )
}