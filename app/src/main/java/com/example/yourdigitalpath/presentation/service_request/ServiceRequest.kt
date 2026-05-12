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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.blqes.digi.presentation.BottomNavBar
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

    LaunchedEffect(serviceName) {
        viewModel.calculateInitialFees(serviceName)
    }

    val requestTypes = viewModel.getRequestTypes(serviceName)
    val requestReasons = viewModel.getRequestReasons(serviceName)
    val deliveryOptions = viewModel.getDeliveryOptions(serviceName)
    val showCopies = viewModel.hasCopiesAndDelivery(serviceName)
    val isFormValid = state.selectedType.isNotEmpty() &&
            state.requestReason.isNotEmpty() &&
            state.deliveryMethod.isNotEmpty() &&
            state.nationalIdNumber.length == 14 &&
            state.phoneNumber.length == 11 &&
            state.nationalIdError == null &&
            state.phoneError == null

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(
                                text = serviceName,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = AppColors.PrimaryLight
                            )
                            Text(
                                text = "حدد نوع الطلب",
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

                Column(modifier = Modifier.padding(16.dp)) {

                    SectionCard {
                        SelectionChipGroup(
                            title = "نوع الطلب",
                            items = requestTypes,
                            selectedItem = state.selectedType,
                            onItemSelected = { viewModel.updateSelectedType(it, serviceName) }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    SectionCard {
                        Text(
                            text = "البيانات الأساسية",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = DarkBlue,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        CustomTextField(
                            value = state.nationalIdNumber,
                            onValueChange = {
                                if (it.length <= 14) viewModel.updateNationalIdNumber(
                                    it
                                )
                            },
                            label = "الرقم القومي (14 رقم)",
                            placeholder = "أدخل الرقم القومي الخاص بك"
                        )
                        if (state.nationalIdError != null) {
                            Text(
                                text = state.nationalIdError!!,
                                color = Color.Red,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        CustomTextField(
                            value = state.phoneNumber,
                            onValueChange = { if (it.length <= 11) viewModel.updatePhoneNumber(it) },
                            label = "رقم الهاتف (11 رقم)",
                            placeholder = "01xxxxxxxxx"
                        )
                        if (state.phoneError != null) {
                            Text(
                                text = state.phoneError!!,
                                color = Color.Red,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    SectionCard {
                        SelectionChipGroup(
                            title = "سبب الطلب",
                            items = requestReasons,
                            selectedItem = state.requestReason,
                            onItemSelected = { viewModel.updateRequestReason(it) }
                        )
                        CustomTextField(
                            value = state.otherReason ?: "",
                            onValueChange = { viewModel.updateOtherReason(it) },
                            label = "سبب آخر (اختياري)",
                            placeholder = "اكتب السبب..."
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    SectionCard {
                        if (showCopies) {
                            Text(
                                text = "عدد النسخ وطريقة التسليم",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = DarkBlue,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            CustomDropdown(
                                label = "عدد النسخ (1-3)",
                                selectedOption = when (state.copiesCount) {
                                    1 -> "نسخة واحدة"
                                    2 -> "نسختان"
                                    else -> "3 نسخ"
                                },
                                options = listOf("نسخة واحدة", "نسختان", "3 نسخ"),
                                onOptionSelected = { option ->
                                    val count = when (option) {
                                        "نسخة واحدة" -> 1
                                        "نسختان" -> 2
                                        else -> 3
                                    }
                                    viewModel.updateCopiesCount(count, serviceName)
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        SelectionChipGroup(
                            title = "طريقة الاستلام",
                            items = deliveryOptions,
                            selectedItem = state.deliveryMethod,
                            onItemSelected = { viewModel.updateDeliveryMethod(it) }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    ActionButton(
                        text = "التالي",
                        onClick = { if (isFormValid) onNext() },
                        enabled = isFormValid
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}