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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import com.blqes.digi.presentation.BottomNavBar
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.ui.components.ActionButton
import com.example.yourdigitalpath.ui.components.CustomDropdown
import com.example.yourdigitalpath.ui.components.SectionCard
import com.example.yourdigitalpath.ui.components.SelectionChipGroup
import com.example.yourdigitalpath.ui.components.StepperComponent
import com.example.yourdigitalpath.ui.components.getServiceTitle
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
    val localizedServiceName = getServiceTitle(serviceName)

    LaunchedEffect(serviceName) {
        viewModel.calculateInitialFees(serviceName)
    }

    val requestTypes = viewModel.getRequestTypes(serviceName)
    val requestReasons = viewModel.getRequestReasons(serviceName)
    val deliveryOptions = viewModel.getDeliveryOptions(serviceName)
    val showCopies = viewModel.hasCopiesAndDelivery(serviceName)

    val isFormValid = state.selectedType.isNotEmpty() &&
            state.requestReason.isNotEmpty() &&
            state.deliveryMethod.isNotEmpty()

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
                                contentDescription = null,
                                tint = AppColors.PrimaryLight
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColors.Primary)
                )
            },
            bottomBar = { BottomNavBar(navController) },
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
                StepperComponent(currentStep = 1)

                Column(modifier = Modifier.padding(16.dp)) {

                    SectionCard {
                        SelectionChipGroup(
                            title = stringResource(R.string.request_type),
                            items = requestTypes,
                            selectedItem = state.selectedType,
                            onItemSelected = { viewModel.updateSelectedType(it, serviceName) }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    SectionCard {
                        SelectionChipGroup(
                            title = stringResource(R.string.request_reason),
                            items = requestReasons,
                            selectedItem = state.requestReason,
                            onItemSelected = { viewModel.updateRequestReason(it) }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    SectionCard {
                        if (showCopies) {
                            Text(
                                text = stringResource(R.string.copies_and_delivery_title),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = AppColors.Primary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            val copyOptions = listOf(
                                stringResource(R.string.one_copy),
                                stringResource(R.string.two_copies),
                                stringResource(R.string.three_copies),
                                stringResource(R.string.four_copies),
                                stringResource(R.string.five_copies)
                            )
                            CustomDropdown(
                                label = stringResource(R.string.copies_count_label),
                                selectedOption = when (state.copiesCount) {
                                    1 -> copyOptions[0]
                                    2 -> copyOptions[1]
                                    3 -> copyOptions[2]
                                    4 -> copyOptions[3]
                                    5 -> copyOptions[4]
                                    else -> "${state.copiesCount} ${stringResource(R.string.copies)}"
                                },
                                options = copyOptions,
                                onOptionSelected = { option ->
                                    val count =
                                        copyOptions.indexOf(option).takeIf { it >= 0 }?.plus(1)
                                            ?: option.filter { it.isDigit() }.toIntOrNull() ?: 1
                                    viewModel.updateCopiesCount(count, serviceName)
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        SelectionChipGroup(
                            title = stringResource(R.string.delivery_method),
                            items = deliveryOptions,
                            selectedItem = state.deliveryMethod,
                            onItemSelected = { viewModel.updateDeliveryMethod(it) }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    ActionButton(
                        text = stringResource(R.string.next),
                        onClick = { if (isFormValid) onNext() },
                        enabled = isFormValid
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
