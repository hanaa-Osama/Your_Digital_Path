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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yourdigitalpath.presentation.order_track.component.DetailsCard
import com.example.yourdigitalpath.presentation.order_track.component.OrderTimelineSection
import com.example.yourdigitalpath.presentation.order_track.component.StatusHighlightCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackingDetailsScreen(
    orderId: String,
    trackingviewModel: TrackingViewModel = hiltViewModel(),
    navController: NavController
) {
    val trackingstate by trackingviewModel.state.collectAsState()

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
                        trackingstate?.let {
//                            Text(
//                                it.orderId,
//                                color = Color(0xFF98A2B3),
//                                fontSize = 14.sp,
//                                fontWeight = FontWeight.Normal
//                            )
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

            val currentOrder = trackingstate
            StatusHighlightCard(currentOrder)

            Spacer(modifier = Modifier.height(20.dp))

            DetailsCard(currentOrder, orderId)


            Spacer(modifier = Modifier.height(20.dp))

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

