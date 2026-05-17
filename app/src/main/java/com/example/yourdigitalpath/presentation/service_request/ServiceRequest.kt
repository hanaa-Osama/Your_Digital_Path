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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.blqes.digi.presentation.BottomNavBar
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.ui.components.ActionButton
import com.example.yourdigitalpath.ui.components.CustomDropdown
import com.example.yourdigitalpath.ui.components.CustomTextField
import com.example.yourdigitalpath.ui.components.SectionCard
import com.example.yourdigitalpath.ui.components.SelectionChipGroup
import com.example.yourdigitalpath.ui.components.StepperComponent
import com.example.yourdigitalpath.ui.theme.AppColors
import com.example.yourdigitalpath.ui.components.getServiceTitle

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

    androidx.compose.runtime.LaunchedEffect(serviceName) {
        viewModel.updateServiceName(serviceName)
    }

    Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
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
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    SectionCard {
                        val fullCopy = stringResource(R.string.full_copy)
                        val shortCopy = stringResource(R.string.short_copy)
                        val certifiedDigital = stringResource(R.string.certified_digital)
                        val lostReplacement = stringResource(R.string.lost_replacement)

                        SelectionChipGroup(
                            title = stringResource(R.string.request_type),
                            items = listOf(fullCopy, shortCopy, certifiedDigital, lostReplacement),
                            selectedItem = when (state.selectedType) {
                                "full_copy" -> fullCopy
                                "short_copy" -> shortCopy
                                "certified_digital" -> certifiedDigital
                                "lost_replacement" -> lostReplacement
                                else -> state.selectedType
                            },
                            onItemSelected = { label ->
                                val slug = when (label) {
                                    fullCopy -> "full_copy"
                                    shortCopy -> "short_copy"
                                    certifiedDigital -> "certified_digital"
                                    lostReplacement -> "lost_replacement"
                                    else -> label
                                }
                                viewModel.updateSelectedType(slug)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    SectionCard {
                        val renewal = stringResource(R.string.renewal)
                        val travel = stringResource(R.string.travel)
                        val work = stringResource(R.string.work)

                        SelectionChipGroup(
                            title = stringResource(R.string.request_reason),
                            items = listOf(renewal, travel, work),
                            selectedItem = when (state.requestReason) {
                                "renewal" -> renewal
                                "travel" -> travel
                                "work" -> work
                                else -> state.requestReason
                            },
                            onItemSelected = { label ->
                                val slug = when (label) {
                                    renewal -> "renewal"
                                    travel -> "travel"
                                    work -> "work"
                                    else -> label
                                }
                                viewModel.updateRequestReason(slug)
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
                            color = AppColors.TextPrimary,
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

                        val officePickup = stringResource(R.string.office_pickup)
                        val delivery = stringResource(R.string.delivery)
                        val digital = stringResource(R.string.digital)

                        SelectionChipGroup(
                            title = stringResource(R.string.delivery_method),
                            items = listOf(officePickup, delivery, digital),
                            selectedItem = when (state.deliveryMethod) {
                                "office_pickup" -> officePickup
                                "delivery" -> delivery
                                "digital" -> digital
                                else -> state.deliveryMethod
                            },
                            onItemSelected = { label ->
                                val slug = when (label) {
                                    officePickup -> "office_pickup"
                                    delivery -> "delivery"
                                    digital -> "digital"
                                    else -> label
                                }
                                viewModel.updateDeliveryMethod(slug)
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