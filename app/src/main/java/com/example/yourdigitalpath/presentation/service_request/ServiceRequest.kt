package com.example.yourdigitalpath.presentation.service_request

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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.blqes.digi.presentation.BottomNavBar
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.ui.components.ActionButton
import com.example.yourdigitalpath.ui.components.BackgroundGray
import com.example.yourdigitalpath.ui.components.CustomDropdown
import com.example.yourdigitalpath.ui.components.CustomTextField
import com.example.yourdigitalpath.ui.components.DarkBlue
import com.example.yourdigitalpath.ui.components.SectionCard
import com.example.yourdigitalpath.ui.components.SelectionChipGroup
import com.example.yourdigitalpath.ui.components.StepperComponent
import com.example.yourdigitalpath.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceRequestScreen(
    serviceName: String,
    navController: NavController,
    viewModel: ServiceRequestViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()

    val configuration = LocalConfiguration.current
    val isArabic = configuration.locales[0].language == "ar"
    val layoutDirection = if (isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = serviceName,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = AppColors.PrimaryLight
                            )
                            Text(
                                text = stringResource(R.string.select_request_type),
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
            },
            bottomBar = {
                BottomNavBar(navController)
            },
            containerColor = BackgroundGray
        ) { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(BackgroundGray)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start
            ) {
                StepperComponent(currentStep = 1)
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    SectionCard {
                        SelectionChipGroup(
                            title = stringResource(R.string.request_type),
                            items = listOf(
                                stringResource(R.string.full_copy),
                                stringResource(R.string.short_copy),
                                stringResource(R.string.certified_digital),
                                stringResource(R.string.lost_replacement)
                            ),
                            selectedItem = state.selectedType,
                            onItemSelected = {
                                viewModel.updateSelectedType(it)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    SectionCard {
                        SelectionChipGroup(
                            title = stringResource(R.string.request_reason),
                            items = listOf(
                                stringResource(R.string.renewal),
                                stringResource(R.string.travel),
                                stringResource(R.string.work)
                            ),
                            selectedItem = state.requestReason,
                            onItemSelected = {
                                viewModel.updateRequestReason(it)
                            }
                        )
                        CustomTextField(
                            value = state.otherReason ?: "",
                            onValueChange = {
                                viewModel.updateOtherReason(it)
                            },
                            label = stringResource(R.string.other_reason_optional),
                            placeholder = stringResource(R.string.write_reason)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    SectionCard {
                        Text(
                            text = stringResource(R.string.copies_and_delivery),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = DarkBlue,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        val oneCopy =
                            stringResource(R.string.one_copy)
                        val twoCopies =
                            stringResource(R.string.two_copies)
                        val copiesWord =
                            stringResource(R.string.copies)
                        val options =
                            (1..3).map {
                                when (it) {
                                    1 -> oneCopy
                                    2 -> twoCopies
                                    else -> "$it $copiesWord"
                                }
                            }
                        CustomDropdown(
                            label = stringResource(R.string.copies_count),
                            selectedOption = when (state.copiesCount) {
                                1 -> oneCopy
                                2 -> twoCopies
                                else -> "${state.copiesCount} $copiesWord"
                            },
                            options = options,
                            onOptionSelected = { option ->
                                val count =
                                    when (option) {
                                        oneCopy -> 1
                                        twoCopies -> 2
                                        else ->
                                            option.split(" ")[0]
                                                .toIntOrNull() ?: 1
                                    }
                                viewModel.updateCopiesCount(count)
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        SelectionChipGroup(
                            title = stringResource(R.string.delivery_method),
                            items = listOf(
                                stringResource(R.string.office_pickup),
                                stringResource(R.string.delivery),
                                stringResource(R.string.digital)
                            ),
                            selectedItem = state.deliveryMethod,
                            onItemSelected = {
                                viewModel.updateDeliveryMethod(it)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    val isFormValid =
                        state.selectedType.isNotEmpty() &&
                                state.requestReason.isNotEmpty() &&
                                state.deliveryMethod.isNotEmpty()
                    ActionButton(
                        text = stringResource(R.string.next),
                        onClick = {
                            if (isFormValid) {
                                onNext()
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}