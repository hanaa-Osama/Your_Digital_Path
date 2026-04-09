package com.example.yourdigitalpath.presentation.service_request

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.ui.components.ActionButton
import com.example.yourdigitalpath.ui.components.CustomDropdown
import com.example.yourdigitalpath.ui.components.CustomTextField
import com.example.yourdigitalpath.ui.components.SectionCard
import com.example.yourdigitalpath.ui.components.SelectionChipGroup

@Composable
fun ServiceRequestStep(
    viewModel: ServiceRequestViewModel = hiltViewModel(),
    onNext: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
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
                modifier = Modifier.padding(bottom = 8.dp)
            )

            val options = (1..3).map { "$it نسخة" }
            CustomDropdown(
                label = "عدد النسخ (1-3)",
                selectedOption = "${state.copiesCount} نسخة",
                options = options,
                onOptionSelected = { option ->
                    val count = option.split(" ")[0].toIntOrNull() ?: 1
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
            },
            modifier = Modifier.alpha(if (isFormValid) 1f else 0.5f)
        )
    }
}
