package com.example.yourdigitalpath.presentation.uploadfile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.presentation.data_entry.certificates.BirthCertificateViewModel
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestViewModel
import com.example.yourdigitalpath.ui.components.ActionButton
import com.example.yourdigitalpath.ui.components.DarkBlue
import com.example.yourdigitalpath.ui.components.GrayText
import com.example.yourdigitalpath.ui.components.PrimaryBlue

@Composable
fun ServiceSummaryScreen(
    serviceName: String,
    serviceRequestViewModel: ServiceRequestViewModel,
    birthCertificateViewModel: BirthCertificateViewModel = hiltViewModel(),
    onConfirm: () -> Unit
) {
    val requestState by serviceRequestViewModel.uiState.collectAsState()
    val personalState by birthCertificateViewModel.uiState.collectAsState()

    // حسبة الرسوم الاجمالية
    val totalFees = requestState.copiesCount * 20

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            bottomBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ActionButton(
                        text = "تأكيد والانتقال للدفع",
                        onClick = onConfirm
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "بالضغط، تقر بصحة جميع البيانات المدخلة",
                        fontSize = 12.sp,
                        color = GrayText
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(PrimaryBlue)
                        .padding(24.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White.copy(alpha = 0.2f))
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(text = "مراجعة الطلب", color = Color.White, fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                Icons.Default.AssignmentTurnedIn,
                                null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "ملخص الطلب",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "راجع البيانات جيداً قبل الدفع",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }

                Column(modifier = Modifier.padding(16.dp)) {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF2F4F7))
                    ) {
                        Column {
                            SummaryRow(label = "الخدمة", value = serviceName)
                            HorizontalDivider(color = Color(0xFFF2F4F7))
                            SummaryRow(
                                label = "نوع الطلب",
                                value = "${requestState.selectedType} – عادي"
                            )
                            HorizontalDivider(color = Color(0xFFF2F4F7))
                            SummaryRow(
                                label = "اسم صاحب الوثيقة",
                                value = personalState.fullName.ifEmpty { "غير متوفر" })
                            HorizontalDivider(color = Color(0xFFF2F4F7))
                            SummaryRow(
                                label = "الرقم القومي",
                                value = personalState.applicantNationalId.ifEmpty { "غير متوفر" })
                            HorizontalDivider(color = Color(0xFFF2F4F7))
                            SummaryRow(
                                label = "مقدم الطلب",
                                value = personalState.relationship.ifEmpty { "صاحب الوثيقة" })
                            HorizontalDivider(color = Color(0xFFF2F4F7))
                            SummaryRow(
                                label = "عدد النسخ",
                                value = if (requestState.copiesCount == 2) "نسختان" else "${requestState.copiesCount} نسخ"
                            )
                            HorizontalDivider(color = Color(0xFFF2F4F7))
                            SummaryRow(
                                label = "طريقة الاستلام",
                                value = requestState.deliveryMethod.ifEmpty { "توصيل للمنزل" })
                            HorizontalDivider(color = Color(0xFFF2F4F7))
                            SummaryRow(
                                label = "وقت الإنجاز",
                                value = "5-7 أيام عمل",
                                valueColor = Color(0xFF3A7D5A)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))


                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF2F4F7))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "المستندات المرفوعة",
                                fontWeight = FontWeight.Bold,
                                color = DarkBlue
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            if (requestState.nationalIdUrl != null) {
                                DocumentCheckItem(name = "البطاقة القومية – وجه وظهر")
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            if (requestState.serviceDocumentUrl != null) {
                                val docName =
                                    if (serviceName.contains("ميلاد")) "شهادة الميلاد القديمة" else "أصل المستند المطلوب"
                                DocumentCheckItem(name = docName)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))


                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEEF4F9)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF98C1D9))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(horizontalAlignment = Alignment.Start) {
                                Text(
                                    text = totalFees.toString(),
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryBlue
                                )
                                Text(text = "جنيه مصري", fontSize = 12.sp, color = GrayText)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "الرسوم الإجمالية",
                                    fontWeight = FontWeight.Bold,
                                    color = DarkBlue
                                )
                                Text(
                                    text = if (requestState.copiesCount == 2) "نسختان × 20 جنيه" else "${requestState.copiesCount} نسخ × 20 جنيه",
                                    fontSize = 12.sp,
                                    color = GrayText
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String, valueColor: Color = DarkBlue) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = GrayText, fontSize = 14.sp)
        Text(text = value, fontWeight = FontWeight.Bold, color = valueColor, fontSize = 14.sp)
    }
}

@Composable
fun DocumentCheckItem(name: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = name, color = GrayText, fontSize = 13.sp)
        Spacer(modifier = Modifier.width(12.dp))
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(Color(0xFFEAF4EE), CircleShape)
                .border(1.dp, Color(0xFF3A7D5A), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Check,
                null,
                tint = Color(0xFF3A7D5A),
                modifier = Modifier.size(12.dp)
            )
        }
    }
}
