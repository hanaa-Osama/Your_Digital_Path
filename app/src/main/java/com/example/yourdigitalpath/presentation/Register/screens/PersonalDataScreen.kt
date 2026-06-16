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

fun toEnglishDigits(input: String): String {
    return input.map { ch ->
        when (ch) {
            '٠' -> '0'; '١' -> '1'; '٢' -> '2'; '٣' -> '3'; '٤' -> '4'
            '٥' -> '5'; '٦' -> '6'; '٧' -> '7'; '٨' -> '8'; '٩' -> '9'
            else -> ch
        }
    }.joinToString("")
}

fun isAllDigits(input: String): Boolean {
    return input.all { ch ->
        ch.isDigit() || ch in '٠'..'٩'
    }
}

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
    val nationalIdAlreadyUsedError = stringResource(R.string.national_id_already_used)
    var isLoading by remember { mutableStateOf(false) }
    var nationalIdError by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Primary)
    ) {
        RegisterTopBar(onBack = onBack)
        RegisterStepsIndicator(currentStep = 1)

        var showErrors by remember { mutableStateOf(false) }
        val isNameValid = fullName.trim().split(" ").size >= 3
        val isNationalIdValid = nationalId.length == 14
        val isBirthDateValid = birthDate.isNotEmpty()
        val phoneEnglish = toEnglishDigits(phone)
        val phoneRegex = Regex("^01[0125][0-9]{8}$")
        val isPhoneValid = phoneEnglish.length == 11 && phoneRegex.matches(phoneEnglish)
        val isFormValid = isNameValid && isNationalIdValid && isBirthDateValid && isPhoneValid

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
                isVerified = isNameValid,
                isError = showErrors && !isNameValid,
                errorMessage = if (fullName.trim().isEmpty()) stringResource(R.string.full_name_required) else stringResource(R.string.full_name_invalid)
            )

            RegisterInputField(
                label = stringResource(R.string.national_id),
                value = nationalId,
                onValueChange = {
                    if (it.length <= 14 && isAllDigits(it)) {
                        nationalId = it
                        viewModel.nationalId = it
                        nationalIdError = ""
                    }
                },
                isVerified = isNationalIdValid && nationalIdError.isEmpty(),
                isError = (showErrors && !isNationalIdValid) || nationalIdError.isNotEmpty(),
                errorMessage = when {
                    nationalIdError.isNotEmpty() -> nationalIdError
                    nationalId.isEmpty() -> stringResource(R.string.national_id_required)
                    else -> stringResource(R.string.national_id_invalid)
                }
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
                    placeholder = stringResource(R.string.birth_date_placeholder),
                    errorMessage = if (showErrors && !isBirthDateValid) stringResource(R.string.birth_date_required) else null
                )
            }
            RegisterInputField(
                label = stringResource(R.string.phone_number),
                value = phone,
                onValueChange = {
                    if (it.length <= 11 && isAllDigits(it)) {
                        phone = it
                        viewModel.phone = it
                    }
                },
                placeholder = stringResource(R.string.phone_placeholder),
                isVerified = isPhoneValid,
                isError = showErrors && !isPhoneValid,
                errorMessage = when {
                    phone.isEmpty() -> stringResource(R.string.phone_required)
                    !phoneEnglish.startsWith("01") || (phoneEnglish.length >= 3 && phoneEnglish[2] !in listOf('0', '1', '2', '5')) -> stringResource(R.string.phone_prefix_invalid)
                    phoneEnglish.length < 11 -> stringResource(R.string.phone_incomplete)
                    else -> stringResource(R.string.invalid_phone)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            RegisterWarningCard(
                message = stringResource(R.string.personal_data_warning)
            )
            Spacer(modifier = Modifier.height(24.dp))
            RegisterButton(
                text = stringResource(R.string.next_account_data),
                enabled = isFormValid && !isLoading,
                onClick = {
                    showErrors = true
                    if (isFormValid) {
                        isLoading = true
                        val englishNationalId = toEnglishDigits(nationalId)

                        viewModel.checkNationalIdAvailable(englishNationalId) { exists ->
                            if (exists) {
                                nationalIdError = nationalIdAlreadyUsedError
                                showErrors = true
                            } else {
                                onNext()
                            }
                            isLoading = false
                        }
                    }
                }
            )
        }
    }
}