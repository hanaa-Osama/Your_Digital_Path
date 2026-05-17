package com.example.yourdigitalpath.presentation.data_entry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.res.stringResource
import com.example.yourdigitalpath.R
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.presentation.data_entry.certificates.BirthCertificateViewModel
import com.example.yourdigitalpath.ui.components.ActionButton
import com.example.yourdigitalpath.ui.components.CustomDatePickerField
import com.example.yourdigitalpath.ui.components.CustomDropdown
import com.example.yourdigitalpath.ui.components.CustomTextField
import com.example.yourdigitalpath.ui.components.SectionCard
import com.example.yourdigitalpath.ui.components.SectionHeader
import com.example.yourdigitalpath.ui.components.SelectionChipGroup
import com.example.yourdigitalpath.ui.components.StepperComponent
import com.example.yourdigitalpath.ui.theme.AppColors

import com.example.yourdigitalpath.ui.components.getServiceTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataScreen(
    serviceName: String,
    onNext: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BirthCertificateViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val localizedServiceName = getServiceTitle(serviceName)
    val egyptGovernorates = listOf(
        stringResource(R.string.cairo),
        stringResource(R.string.giza),
        stringResource(R.string.alexandria),
        stringResource(R.string.dakahlia),
        stringResource(R.string.red_sea),
        stringResource(R.string.beheira),
        stringResource(R.string.fayoum),
        stringResource(R.string.gharbia),
        stringResource(R.string.ismailia),
        stringResource(R.string.monufia),
        stringResource(R.string.minya),
        stringResource(R.string.qalyubia),
        stringResource(R.string.new_valley),
        stringResource(R.string.suez),
        stringResource(R.string.sharqia),
        stringResource(R.string.aswan),
        stringResource(R.string.assiut),
        stringResource(R.string.beni_suef),
        stringResource(R.string.port_said),
        stringResource(R.string.damietta),
        stringResource(R.string.south_sinai),
        stringResource(R.string.kafr_el_sheikh),
        stringResource(R.string.matrouh),
        stringResource(R.string.luxor),
        stringResource(R.string.qena),
        stringResource(R.string.north_sinai),
        stringResource(R.string.sohag)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
            TopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = localizedServiceName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppColors.PrimaryLight
                        )
                        Text(
                            text = stringResource(R.string.step_2_document_owner),
                            fontSize = 12.sp,
                            color = AppColors.PrimaryLight
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBackIos,
                            contentDescription = stringResource(R.string.back),
                            tint = AppColors.PrimaryLight
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.Primary
                )
            )
            StepperComponent(currentStep = 2)
            Column(modifier = Modifier.padding(16.dp)) {
                SectionCard {
                    SectionHeader(stringResource(R.string.document_owner_data))

                    CustomTextField(
                        value = uiState.fullName,
                        onValueChange = { viewModel.updateFullName(it) },
                        label = stringResource(R.string.full_name_arabic),
                        placeholder = stringResource(R.string.full_name_placeholder),
                        isValid = uiState.fullName.trim().split(" ").size >= 4,
                        errorMessage = uiState.fullNameError
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.birth_date),
                        color = AppColors.TextHint,
                        fontSize = 13.sp
                    )
                    CustomDatePickerField(
                        value = uiState.dateOfBirth,
                        onValueChange = { viewModel.updateDateOfBirth(it) },
                        placeholder = stringResource(R.string.birth_date_placeholder),
                        leadingIcon = Icons.Default.CalendarMonth,
                        errorMessage = uiState.dateOfBirthError
                    )

                    CustomDropdown(
                        label = stringResource(R.string.birth_governorate),
                        selectedOption = uiState.governorate.ifEmpty {
                            stringResource(R.string.choose_governorate)
                        },
                        options = egyptGovernorates,
                        onOptionSelected = { viewModel.updateGovernorate(it) },
                        errorMessage = uiState.governorateError
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                SectionCard {
                    SectionHeader(stringResource(R.string.applicant_data))

                    CustomTextField(
                        value = uiState.applicantNationalId,
                        onValueChange = { viewModel.updateNationalId(it) },
                        label = stringResource(R.string.national_id_14),
                        placeholder = "2990115012345XX",
                        isValid = uiState.applicantNationalId.length == 14,
                        errorMessage = uiState.applicantNationalIdError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    CustomTextField(
                        value = uiState.applicantPhone,
                        onValueChange = { viewModel.updatePhone(it) },
                        label = stringResource(R.string.phone_number),
                        placeholder = "010XXXXXXXX",
                        leadingIcon = Icons.Default.Phone,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        errorMessage = uiState.applicantPhoneError
                    )

                    SelectionChipGroup(
                        title = stringResource(R.string.relationship),
                        items = listOf(
                            stringResource(R.string.document_owner),
                            stringResource(R.string.guardian),
                            stringResource(R.string.agent)
                        ),
                        selectedItem = uiState.relationship,
                        onItemSelected = { viewModel.updateRelationship(it) }
                    )
                    if (uiState.relationshipError != null) {
                        Text(
                            text = uiState.relationshipError!!,
                            color = Color.Red,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                ActionButton(
                    text = stringResource(R.string.next),
                    onClick = {
                        viewModel.submitForm(onSuccess = onNext)
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
}
