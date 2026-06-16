package com.example.yourdigitalpath.presentation.order_track

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.presentation.order_track.component.DetailsCard
import com.example.yourdigitalpath.presentation.order_track.component.OrderTimelineSection
import com.example.yourdigitalpath.presentation.order_track.component.StatusHighlightCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackingDetailsScreen(
    orderId: String,
    trackingviewModel: TrackingViewModel = hiltViewModel(),
    navController: NavController,
    onBack: () -> Unit = { navController.popBackStack() }
) {
    val context = LocalContext.current
    val trackingstate by trackingviewModel.state.collectAsState()

    LaunchedEffect(orderId) {
        trackingviewModel.startTracking(orderId)
    }

    Scaffold(
        containerColor = com.example.yourdigitalpath.ui.theme.AppColors.Background,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(
                                com.example.yourdigitalpath.ui.theme.AppColors.Primary,
                                if (com.example.yourdigitalpath.ui.theme.LocalDarkTheme.current)
                                    Color(0xFF0F1929)
                                else
                                    Color(0xFF293241)
                            )
                        )
                    )
                    .padding(top = 16.dp, bottom = 24.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                onBack()
                            },
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.White.copy(alpha = 0.2f), CircleShape)
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back),
                                tint = Color.White
                            )
                        }
                        
                        Text(
                            text = stringResource(R.string.tracking_details),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.weight(1f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                        Spacer(modifier = Modifier.size(40.dp))
                    }

                    trackingstate?.let { order ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 8.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = stringResource(
                                    R.string.order_number_with_id,
                                    order.orderId
                                ),
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        Toast.makeText(
                            context,
                            "سيتم توفير هذه الميزة مستقبلاً (Future Work)",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, com.example.yourdigitalpath.ui.theme.AppColors.Border)
                ) {
                    Text(
                        text = stringResource(R.string.inquiry)
                        , color = com.example.yourdigitalpath.ui.theme.AppColors.TextPrimary,
                        fontSize = 16.sp
                    )
                }
                Button(
                    onClick = {
                        Toast.makeText(
                            context,
                            "سيتم توفير هذه الميزة مستقبلاً (Future Work)",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = com.example.yourdigitalpath.ui.theme.AppColors.Primary)
                ) {
                    Text(
                        text = stringResource(R.string.track_shipping),
                        color = Color.White,
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
                .background(com.example.yourdigitalpath.ui.theme.AppColors.Background)
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
                    CircularProgressIndicator(color = com.example.yourdigitalpath.ui.theme.AppColors.Primary)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

