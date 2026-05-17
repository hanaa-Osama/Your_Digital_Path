package com.example.yourdigitalpath.presentation.order_track.component

import androidx.compose.foundation.background
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
import androidx.compose.ui.res.stringResource
import com.example.yourdigitalpath.R
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.domain.model.TrackingStep

import com.example.yourdigitalpath.ui.theme.AppColors

@Composable
fun OrderTimelineSection(steps: List<TrackingStep>) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                    text = stringResource(
                        R.string.order_tracking_path
                    ),
                fontWeight = FontWeight.Black,
                fontSize = 18.sp,
                color = AppColors.TextPrimary,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))

            steps.forEachIndexed { index, step ->
                TimelineItem(
                    status = stringResource(step.title),
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
    stepStatus: String,
    isLast: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(30.dp)
        ) {
            Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.weight(1f)) {
                if (!isLast) {
                    Box(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .width(2.dp)
                            .fillMaxHeight()
                            .background(
                                if (stepStatus == "completed") AppColors.Success else AppColors.Border,
                                shape = RoundedCornerShape(2.dp)
                            )
                    )
                }

                when (stepStatus) {
                    "completed" -> {
                        Surface(
                            shape = CircleShape,
                            color = AppColors.SuccessBg,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null,
                                tint = AppColors.Success,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }

                    "current" -> {
                        Surface(
                            shape = CircleShape,
                            color = AppColors.Surface,
                            border = androidx.compose.foundation.BorderStroke(
                                2.dp,
                                AppColors.Primary
                            ),
                            modifier = Modifier.size(24.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(6.dp)
                                    .background(AppColors.Primary, CircleShape)
                            )
                        }
                    }

                    else -> {
                        Surface(
                            shape = CircleShape,
                            color = AppColors.Surface,
                            border = androidx.compose.foundation.BorderStroke(
                                2.dp,
                                AppColors.Border
                            ),
                            modifier = Modifier.size(24.dp)
                        ) {}
                    }
                }
            }
            if (!isLast) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = status,
            fontSize = 14.sp,
            fontWeight = if (stepStatus == "current") FontWeight.Bold else FontWeight.Medium,
            color = when (stepStatus) {
                "completed" -> AppColors.Success
                "current" -> AppColors.TextPrimary
                else -> AppColors.TextHint
            },
            modifier = Modifier
                .padding(top = 2.dp)
                .weight(2f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = if (date.isEmpty()) "-" else date,
            fontSize = 13.sp,
            color = AppColors.TextHint,
            modifier = Modifier
                .padding(top = 2.dp)
                .weight(1f)
        )
    }
}

