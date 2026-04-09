package com.example.yourdigitalpath.presentation.feature_upload_summary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.domain.model.ServiceRequest
import com.example.yourdigitalpath.presentation.view_model_summary.SummaryUiState
import com.example.yourdigitalpath.presentation.view_model_summary.SummaryViewModel

@Composable
fun RequestSummaryScreen(
    viewModel: SummaryViewModel,
    serviceRequest: ServiceRequest,
    onPaymentClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            SummaryHeaderSection()

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                item { Spacer(modifier = Modifier.height(16.dp)) }

                item {
                    SummaryDetailsCard(items = serviceRequest.formData.toList())
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                item {
                    UploadedDocsCard(docs = serviceRequest.documentUrls)
                }

                item { Spacer(modifier = Modifier.height(100.dp)) }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color.White)
                .padding(16.dp)
        ) {
            BottomPaymentSection(
                isLoading = uiState is SummaryUiState.Loading,
                onConfirm = {
                    onPaymentClick()
                }
            )
        }
    }
}

@Composable
fun SummaryDetailsCard(items: List<Pair<String, String>>) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            items.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.second,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2D4B73)
                    )
                    Text(text = item.first, color = Color.Gray, fontSize = 14.sp)
                }
                if (index < items.lastIndex) HorizontalDivider(
                    color = Color(0xFFF1F1F1),
                    thickness = 0.5.dp
                )
            }
        }
    }
}

@Composable
fun BottomPaymentSection(isLoading: Boolean, onConfirm: () -> Unit) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("الرسوم الإجمالية", color = Color.Gray, fontSize = 12.sp)
                Text(
                    "40 جنيه مصري",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color(0xFF2D4B73)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onConfirm,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D4B73)),
            enabled = !isLoading
        ) {
            if (isLoading) CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(24.dp)
            )
            else Text("تأكيد والانتقال للدفع", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun UploadedDocsCard(docs: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "المستندات المرفوعة",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D4B73),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            docs.forEach { doc ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = doc, fontSize = 14.sp, color = Color.DarkGray)
                }
            }
        }
    }
}

@Composable
fun SummaryHeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(
                color = Color(0xFF2D4B73),
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "ملخص الطلب",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "راجع البيانات جيداً قبل الدفع",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
        }
    }
}