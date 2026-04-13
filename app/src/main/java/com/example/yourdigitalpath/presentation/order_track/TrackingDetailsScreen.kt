package com.example.yourdigitalpath.presentation.order_track

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yourdigitalpath.presentation.order_track.component.OrderTimelineSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackingDetailsScreen(
    orderId: String,
    trackingviewModel: TrackingViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by trackingviewModel.state.collectAsState()

    LaunchedEffect(orderId) {
        trackingviewModel.startTracking(orderId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "تفاصيل الطلب",
                            color = Color(0xFF1D2939),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        state?.let {
                            Text(
                                it.orderId,
                                color = Color(0xFF98A2B3),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .padding(8.dp)
                            .size(40.dp)
                            .background(Color(0xFFF9FAFB), CircleShape)
                            .border(BorderStroke(1.dp, Color(0xFFEAECF0)), CircleShape)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF667085)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /* More actions */ },
                        modifier = Modifier
                            .padding(8.dp)
                            .size(40.dp)
                            .background(Color(0xFFF9FAFB), CircleShape)
                            .border(BorderStroke(1.dp, Color(0xFFEAECF0)), CircleShape)
                    ) {
                        Icon(
                            Icons.Default.MoreHoriz,
                            contentDescription = "More",
                            tint = Color(0xFF667085)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { /* Inquiry */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFFEAECF0))
                ) {
                    Text("استفسار", color = Color(0xFF344054), fontSize = 16.sp)
                }
                Button(
                    onClick = { /* Track Shipping */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0EAF5))
                ) {
                    Text(
                        "تتبع الشحن",
                        color = Color(0xFF3B5474),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFFDFDFD))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Status Highlight Card
            val currentOrder = state
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9EB)),
                border = BorderStroke(1.dp, Color(0xFFFFE4A0))
            ) {
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            currentOrder?.steps?.findLast { it.status == "current" || it.status == "completed" }?.title
                                ?: "قيد المراجعة",
                            color = Color(0xFFB54708),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            "مراجعة المستندات - 45%",
                            color = Color(0xFFD48D3B),
                            fontSize = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(Color.White, RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.AccessTime,
                            contentDescription = null,
                            tint = Color(0xFFB54708),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
                // Optional "Ongoing" badge
                Box(modifier = Modifier.padding(start = 20.dp, bottom = 20.dp)) {
                    Surface(
                        color = Color(0xFF937126),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            "جاري",
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFEAECF0))
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    DetailRow("رقم الطلب", currentOrder?.orderId ?: orderId)
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = Color(0xFFF2F4F7)
                    )
                    DetailRow("نوع الخدمة", currentOrder?.serviceType ?: "تجديد بطاقة الهوية")
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = Color(0xFFF2F4F7)
                    )
                    DetailRow("تاريخ التقديم", currentOrder?.date ?: "2 أبريل 2025")
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = Color(0xFFF2F4F7)
                    )
                    DetailRow("طريقة الاستلام", currentOrder?.deliveryMethod ?: "توصيل للمنزل")
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = Color(0xFFF2F4F7)
                    )
                    DetailRow(
                        "المبلغ المدفوع",
                        "${currentOrder?.price ?: "35"} جنيه",
                        valueColor = Color(0xFF067647)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Timeline Section
            if (currentOrder != null) {
                OrderTimelineSection(currentOrder.steps)
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF3B5474))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun DetailRow(label: String, value: String, valueColor: Color = Color(0xFF1D2939)) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = value,
            color = valueColor,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = label,
            color = Color(0xFF98A2B3),
            fontSize = 14.sp,
            textAlign = TextAlign.Right,
            modifier = Modifier.width(100.dp)
        )
    }
}
