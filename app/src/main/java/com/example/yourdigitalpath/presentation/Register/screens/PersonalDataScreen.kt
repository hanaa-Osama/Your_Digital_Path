package com.example.yourdigitalpath.presentation.Register.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.presentation.Register.RegisterViewModel
import com.example.yourdigitalpath.presentation.Register.components.*
import com.example.yourdigitalpath.ui.components.CustomDatePickerField
import com.example.yourdigitalpath.ui.theme.AppColors

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
            .background(AppColors.Primary)
    ) {
            RegisterTopBar(onBack = onBack)
            RegisterStepsIndicator(currentStep = 1)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        AppColors.Surface,
                        RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                    )
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                RegisterSectionHeader(
                    title = stringResource(R.string.personal_data)
                )
                Spacer(modifier = Modifier.height(20.dp))
                RegisterInputField(
                    label = stringResource(R.string.full_name),
                    value = fullName,
                    onValueChange = { value ->
                        val filteredText = value
                            .replace(Regex("[^\\p{L}\\s]"), "")
                            .replace(Regex("\\s+"), " ")

                        fullName = filteredText
                        viewModel.fullName = filteredText
                    },
                    isVerified = fullName.trim().split(" ").size >= 3
                )
                RegisterInputField(
                    label = stringResource(R.string.national_id),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    CustomDatePickerField(
                        value = birthDate,
                        onValueChange = {
                            birthDate = it
                            viewModel.birthDate = it
                        },
                        leadingIcon = Icons.Outlined.DateRange,
                        placeholder = stringResource(R.string.birth_date_placeholder)
                    )
                }
                RegisterInputField(
                    label = stringResource(R.string.phone_number),
                    value = phone,
                    onValueChange = {
                        if (it.length <= 11 && it.all { ch -> ch.isDigit() }) {
                            phone = it
                            viewModel.phone = it
                        }
                    },
                    placeholder = stringResource(R.string.phone_placeholder),
                    isVerified = phone.length == 11,
                    isError = phone.isNotEmpty() && phone.length != 11,
                    errorMessage = stringResource(R.string.invalid_phone)
                )
                Spacer(modifier = Modifier.height(16.dp))
                RegisterWarningCard(
                    message = stringResource(R.string.personal_data_warning)
                )
                Spacer(modifier = Modifier.height(24.dp))
                RegisterButton(
                    text = stringResource(R.string.next_account_data),
                    onClick = { onNext() }
                )
            }
        }
}