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
                text = "مراجعة طلب الخدمة",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            SectionCard {
                SummaryDetailRow(label = "نوع الطلب", value = state.selectedType)
                SummaryDetailRow(label = "سبب الطلب", value = state.requestReason)
                SummaryDetailRow(label = "عدد النسخ", value = "${state.copiesCount}")
                SummaryDetailRow(label = "طريقة الاستلام", value = state.deliveryMethod)
            }

            Spacer(modifier = Modifier.height(16.dp))

            SectionCard {
                Text(
                    text = "المستندات المرفقة",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = if (state.documentsUrls.isNotEmpty()) "تم رفع المستند بنجاح ✅" else "لم يتم رفع مستندات ❌",
                    color = if (state.documentsUrls.isNotEmpty()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
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