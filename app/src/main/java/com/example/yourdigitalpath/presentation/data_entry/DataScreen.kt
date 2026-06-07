package com.example.yourdigitalpath.presentation.data_entry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.presentation.service_request.DataField
import com.example.yourdigitalpath.presentation.service_request.FieldType
import com.example.yourdigitalpath.presentation.service_request.RelationshipType
import com.example.yourdigitalpath.presentation.service_request.ServiceConfigs
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestViewModel
import com.example.yourdigitalpath.presentation.service_request.ValidationType
import com.example.yourdigitalpath.ui.components.ActionButton
import com.example.yourdigitalpath.ui.components.CustomDatePickerField
import com.example.yourdigitalpath.ui.components.CustomDropdown
import com.example.yourdigitalpath.ui.components.CustomTextField
import com.example.yourdigitalpath.ui.components.InputTypes
import com.example.yourdigitalpath.ui.components.SectionCard
import com.example.yourdigitalpath.ui.components.SectionHeader
import com.example.yourdigitalpath.ui.components.StepperComponent
import com.example.yourdigitalpath.ui.components.getServiceTitle
import com.example.yourdigitalpath.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataScreen(
    serviceName: String,
    onNext: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ServiceRequestViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val isStep2Valid by viewModel.isStep2Valid.collectAsState()
    val config = viewModel.getServiceConfig(serviceName)
    val localizedServiceName = getServiceTitle(serviceName)

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
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
                                contentDescription = null,
                                tint = AppColors.PrimaryLight
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColors.Primary)
                )
            },
            containerColor = AppColors.Background
        ) { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(AppColors.Background)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start
            ) {
                StepperComponent(currentStep = 2)

                if (config != null) {
                    val groupedFields = config.dataFields.groupBy { it.sectionRes }

                    Column(modifier = Modifier.padding(16.dp)) {
                        for ((sectionRes, fields) in groupedFields) {
                            SectionCard {
                                SectionHeader(stringResource(sectionRes))

                                for (field in fields) {
                                    if (field.isRequired(uiState.selectedType)) {
                                        DynamicFieldRenderer(
                                            field = field,
                                            value = uiState.dataValues[field.id] ?: "",
                                            error = uiState.dataErrors[field.id],
                                            onValueChange = { newValue ->
                                                viewModel.updateDataValue(
                                                    field.id,
                                                    newValue,
                                                    field.validation
                                                )
                                            }
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        ActionButton(
                            text = stringResource(R.string.next),
                            onClick = onNext,
                            enabled = isStep2Valid
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun DynamicFieldRenderer(
    field: DataField,
    value: String,
    error: String?,
    onValueChange: (String) -> Unit
) {
    val label = stringResource(field.labelRes)
    val placeholder = if (field.placeholderRes != 0) stringResource(field.placeholderRes) else ""

    when (field.type) {
        FieldType.DATE -> {
            Text(
                text = label,
                color = AppColors.TextHint,
                fontSize = 13.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            CustomDatePickerField(
                value = value,
                onValueChange = onValueChange,
                placeholder = placeholder.ifEmpty { stringResource(R.string.select_date) },
                errorMessage = error
            )
        }

        FieldType.DROPDOWN -> {
            val options = when {
                field.id.contains("gov") -> ServiceConfigs.getGovernorates()
                field.id == "applicant_relation" -> when (field.relationshipType) {
                    RelationshipType.MARRIAGE -> listOf(
                        stringResource(R.string.relationship_husband),
                        stringResource(R.string.relationship_wife),
                        stringResource(R.string.agent)
                    )

                    RelationshipType.DEATH -> listOf(
                        stringResource(R.string.relationship_son_daughter),
                        stringResource(R.string.relationship_sibling),
                        stringResource(R.string.relationship_spouse),
                        stringResource(R.string.agent)
                    )

                    RelationshipType.DIVORCE -> listOf(
                        stringResource(R.string.relationship_husband),
                        stringResource(R.string.relationship_wife),
                        stringResource(R.string.agent)
                    )

                    else -> listOf(  // GENERAL — شهادة ميلاد
                        stringResource(R.string.document_owner),
                        stringResource(R.string.guardian),
                        stringResource(R.string.agent)
                    )
                }

                else -> listOf(
                    stringResource(R.string.document_owner),
                    stringResource(R.string.guardian),
                    stringResource(R.string.agent)
                )
            }
            CustomDropdown(
                label = label,
                selectedOption = value.ifEmpty { "" },
                placeholder = placeholder.ifEmpty { stringResource(R.string.select_from_list) },
                options = options,
                onOptionSelected = onValueChange,
                errorMessage = error
            )
        }

        FieldType.PHONE -> {
            CustomTextField(
                value = value,
                onValueChange = onValueChange,
                label = label,
                placeholder = placeholder.ifEmpty { stringResource(R.string.phone_placeholder) },
                errorMessage = error,
                inputType = InputTypes.PHONE
            )
        }

        else -> {
            CustomTextField(
                value = value,
                onValueChange = onValueChange,
                label = label,
                placeholder = placeholder,
                errorMessage = error,
                inputType = if (field.validation == ValidationType.NATIONAL_ID) InputTypes.NATIONAL_ID else InputTypes.DEFAULT
            )
        }
    }
}