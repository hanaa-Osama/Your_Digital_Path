package com.example.yourdigitalpath.presentation.service_request

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.ui.components.ActionButton
import com.example.yourdigitalpath.ui.components.SectionCard

@Composable
fun ServiceRequestScreen(
    viewModel: ServiceRequestViewModel,
    onConfirm: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "مراجعة طلب ${state.selectedType}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            SectionCard {
                SummaryDetailRow(label = "نوع الخدمة", value = state.selectedType)

                if (state.requestReason.isNotEmpty()) {
                    SummaryDetailRow(label = "سبب الطلب", value = state.requestReason)
                }

                if (state.copiesCount > 0) {
                    SummaryDetailRow(label = "عدد النسخ", value = "${state.copiesCount}")
                }

                if (state.deliveryMethod.isNotEmpty()) {
                    SummaryDetailRow(label = "طريقة الاستلام", value = state.deliveryMethod)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (state.documentsUrls.isNotEmpty()) {
                SectionCard {
                    Text(
                        text = "المستندات المرفقة",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "تم رفع عدد (${state.documentsUrls.size}) مستند بنجاح ✅",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        ActionButton(
            text = "تأكيد الطلب والحفظ",
            onClick = {
                viewModel.saveServiceRequest {
                    onConfirm()
                }
            }
        )
    }
}

@Composable
fun SummaryDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, fontWeight = FontWeight.Medium)
    }
}