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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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
    onNext: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ServiceRequestViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

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
                                contentDescription = "Back",
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
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionCard {
                        SelectionChipGroup(
                            title = "نوع الطلب",
                            items = listOf("نسخة كاملة", "نسخة مختصرة", "رقمية موثقة", "بدل فاقد"),
                            selectedItem = state.selectedType,
                            onItemSelected = { viewModel.updateSelectedType(it) }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    SectionCard {
                        SelectionChipGroup(
                            title = "سبب الطلب",
                            items = listOf("تجديد", "سفر", "عمل"),
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
                        Text(
                            text = "عدد النسخ وطريقة التسليم",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = DarkBlue,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        val options =
                            (1..3).map { if (it == 1) "نسخة واحدة" else if (it == 2) "نسختان" else "$it نسخ" }
                        CustomDropdown(
                            label = "عدد النسخ (1-3)",
                            selectedOption = when (state.copiesCount) {
                                1 -> "نسخة واحدة"
                                2 -> "نسختان"
                                else -> "${state.copiesCount} نسخ"
                            },
                            options = options,
                            onOptionSelected = { option ->
                                val count = when (option) {
                                    "نسخة واحدة" -> 1
                                    "نسختان" -> 2
                                    else -> option.split(" ")[0].toIntOrNull() ?: 1
                                }
                                viewModel.updateCopiesCount(count)
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        SelectionChipGroup(
                            title = "طريقة الاستلام",
                            items = listOf("في المكتب", "توصيل", "رقمي"),
                            selectedItem = state.deliveryMethod,
                            onItemSelected = { viewModel.updateDeliveryMethod(it) }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    val isFormValid = state.selectedType.isNotEmpty() &&
                            state.requestReason.isNotEmpty() &&
                            state.deliveryMethod.isNotEmpty()

                    ActionButton(
                        text = "التالي",
                        onClick = {
                            if (isFormValid) {
                                onNext()
                                viewModel.saveServiceRequest { }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }

    }
}
