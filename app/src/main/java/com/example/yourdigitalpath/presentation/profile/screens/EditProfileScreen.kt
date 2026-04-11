package com.example.yourdigitalpath.presentation.profile.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.material3.Scaffold
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.presentation.profile.component.ActionButton
import com.example.yourdigitalpath.presentation.profile.component.ProfileSimpleTopBar
import com.example.yourdigitalpath.presentation.profile.component.ProfileTextField
import com.example.yourdigitalpath.presentation.viewModel.ProfileViewModel
import com.example.yourdigitalpath.ui.theme.AppColors

@Composable
fun EditProfileScreen(
    onBackClick: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val user by viewModel.userProfile.collectAsState()

    var name     by remember(user) { mutableStateOf(user?.name          ?: "") }
    var email    by remember(user) { mutableStateOf(user?.email         ?: "") }
    var phone    by remember(user) { mutableStateOf(user?.phoneNumber   ?: "") }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                ProfileSimpleTopBar(
                    title = "بياناتي الشخصية",
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
            ) {
                ProfileTextField(
                    label = "الاسم بالكامل",
                    value = name,
                    onValueChange = { name = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                ProfileTextField(
                    label = "البريد الإلكتروني",
                    value = email,
                    onValueChange = { email = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                ProfileTextField(
                    label = "رقم الهاتف",
                    value = phone,
                    onValueChange = { phone = it }
                )

                Spacer(modifier = Modifier.weight(1f))

                ActionButton(
                    text = "حفظ التعديلات",
                    onClick = {
                        user?.copy(
                            name = name,
                            email = email,
                            phoneNumber = phone
                        )?.let { viewModel.updateProfile(it) }
                        onBackClick()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, locale = "ar")
@Composable
fun PreviewEditProfileScreen() {
    EditProfileScreen(onBackClick = {})
}