package com.example.yourdigitalpath.presentation.Register.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.presentation.Register.RegisterViewModel
import com.example.yourdigitalpath.presentation.Register.components.*
import com.example.yourdigitalpath.ui.components.CustomDatePickerField
import com.example.yourdigitalpath.ui.components.PrimaryBlue

@Composable
fun PersonalDataScreen(
    onBack: () -> Unit = {},
    onNext: () -> Unit = {},
    viewModel: RegisterViewModel = hiltViewModel()
) {
    var fullName by remember { mutableStateOf(viewModel.fullName) }
    var nationalId by remember { mutableStateOf(viewModel.nationalId) }
    var birthDate by remember { mutableStateOf(viewModel.birthDate) }
    var phone by remember { mutableStateOf(viewModel.phone) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF435D82))
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            RegisterTopBar(onBack = onBack)
            RegisterStepsIndicator(currentStep = 1)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            RegisterSectionHeader(title = "البيانات الشخصية")
            Spacer(modifier = Modifier.height(20.dp))

            RegisterInputField(
                label = "الاسم الكامل",
                value = fullName,
                onValueChange = {
                    fullName = it
                    viewModel.fullName = it
                },
                isVerified = fullName.trim().split(" ").size >= 3
            )

            RegisterInputField(
                label = "الرقم القومي (14 رقم)",
                value = nationalId,
                onValueChange = {
                    if (it.length <= 14 && it.all { ch -> ch.isDigit() }) {
                        nationalId = it
                        viewModel.nationalId = it
                    }
                },
                isVerified = nationalId.length == 14
            )

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    CustomDatePickerField(
                        value = birthDate,
                        onValueChange = {
                            birthDate = it
                            viewModel.birthDate = it
                        },
                        leadingIcon = Icons.Outlined.DateRange,
                        placeholder = "1990 / 01 / 15"
                    )
                }
            }

            RegisterInputField(
                label = "رقم الهاتف",
                value = phone,
                onValueChange = {
                    if (it.length <= 11 && it.all { ch -> ch.isDigit() }) {
                        phone = it
                        viewModel.phone = it
                    }
                },
                placeholder = "010XXXXXXXX",
                isVerified = phone.length == 11,
                isError = phone.isNotEmpty() && phone.length != 11,
                errorMessage = "رقم الهاتف غير صحيح"
            )

            Spacer(modifier = Modifier.height(16.dp))

            RegisterWarningCard(
                message = "تأكد من إدخال بياناتك كما هي في بطاقة الهوية الوطنية لضمان صحة الطلبات"
            )

            Spacer(modifier = Modifier.height(24.dp))

            RegisterButton(
                text = "التالي — بيانات الحساب",
                onClick = { onNext() }
            )
        }
    }
}