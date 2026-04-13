package com.example.yourdigitalpath.presentation.order_track.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.domain.model.TrackingStep

@Composable
fun OrderTimelineSection(steps: List<TrackingStep>) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEAECF0))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "مسار الطلب",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF1D2939),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right
            )
            Spacer(modifier = Modifier.height(24.dp))

            steps.forEachIndexed { index, step ->
                TimelineItem(
                    status = step.title,
                    date = step.timestamp,
                    stepStatus = step.status,
                    isLast = index == steps.size - 1
                )
            }
        }
    }
}

@Composable
fun TimelineItem(
    status: String,
    date: String,
    stepStatus: String, // completed, current, or pending
    isLast: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.End
    ) {

        Text(
            text = if (date.isEmpty()) "-" else date,
            fontSize = 13.sp,
            color = Color(0xFF98A2B3),
            modifier = Modifier
                .padding(top = 2.dp)
                .weight(1f),
            textAlign = TextAlign.Right
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = status,
            fontSize = 14.sp,
            fontWeight = if (stepStatus == "current") FontWeight.Bold else FontWeight.Medium,
            color = when (stepStatus) {
                "completed" -> Color(0xFF067647)
                "current" -> Color(0xFF1D2939)
                else -> Color(0xFF98A2B3)
            },
            modifier = Modifier
                .padding(top = 2.dp)
                .weight(2f),
            textAlign = TextAlign.Right
        )

        Spacer(modifier = Modifier.width(16.dp))


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(30.dp)
        ) {
            Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.weight(1f)) {
                if (!isLast) {
                    Box(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .width(3.dp)
                            .fillMaxHeight()
                            .background(
                                if (stepStatus == "completed") Color(0xFF067647) else Color(
                                    0xFFF2F4F7
                                ),
                                shape = RoundedCornerShape(2.dp)
                            )
                    )
                }

                when (stepStatus) {
                    "completed" -> {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFE7F4EE),
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null,
                                tint = Color(0xFF067647),
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                    }

                    "current" -> {
                        Surface(
                            shape = CircleShape,
                            color = Color.White,
                            border = androidx.compose.foundation.BorderStroke(
                                2.dp,
                                Color(0xFF3B5474)
                            ),
                            modifier = Modifier.size(28.dp)
                        ) {}
                    }

                    else -> {
                        Surface(
                            shape = CircleShape,
                            color = Color.White,
                            border = androidx.compose.foundation.BorderStroke(
                                3.dp,
                                Color(0xFFF2F4F7)
                            ),
                            modifier = Modifier.size(28.dp)
                        ) {}
                    }
                }
            }
            if (!isLast) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
