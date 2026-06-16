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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.R
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
                    title = step.title,
                    description = step.description,
                    date = step.timestamp,
                    stepStatus = step.status,
                    isFirst = index == 0,
                    isLast = index == steps.size - 1
                )
            }
        }
    }
}

@Composable
fun TimelineItem(
    title: String,
    description: String?,
    date: String,
    stepStatus: String,
    isLast: Boolean,
    isFirst: Boolean = false
) {
    fun String.formatAsCompleted(): String {
        return when {
            this == "قيد المراجعة" -> "تم المراجعة"
            this.startsWith("جاري ") -> this.replaceFirst("جاري ", "تم ")
            else -> this
        }
    }

    val displayTitle = if (stepStatus == "completed") title.formatAsCompleted() else title
    val displayDate = if (stepStatus == "completed") date.formatAsCompleted() else date
    val displayDescription =
        if (stepStatus == "completed") description?.formatAsCompleted() else description

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        // Connector Column
        Box(
            modifier = Modifier
                .width(30.dp)
                .fillMaxHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            // Vertical Line
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .padding(top = 12.dp) // Start from center of icon
                        .width(2.dp)
                        .fillMaxHeight()
                        .background(
                            if (stepStatus == "completed") AppColors.Success else AppColors.Border.copy(
                                alpha = 0.5f
                            ),
                            shape = RoundedCornerShape(1.dp)
                        )
                )
            }

            // For items after the first, draw line to connect from top
            if (!isFirst) {
                Box(
                    modifier = Modifier
                        .height(12.dp) // End at center of icon
                        .width(2.dp)
                        .background(
                            if (stepStatus == "completed" || stepStatus == "current") AppColors.Success else AppColors.Border.copy(
                                alpha = 0.5f
                            ),
                            shape = RoundedCornerShape(1.dp)
                        )
                )
            }

            // Step Indicator
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(AppColors.Surface, CircleShape),
                contentAlignment = Alignment.Center
            ) {
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
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Content Column
        Column(
            modifier = Modifier
                .padding(bottom = 24.dp) // Add spacing between steps
                .weight(1f)
        ) {
            Text(
                text = displayTitle.ifEmpty { title }.ifEmpty { "-" },
                fontSize = 15.sp,
                fontWeight = if (stepStatus == "current") FontWeight.Bold else FontWeight.SemiBold,
                color = when (stepStatus) {
                    "completed" -> AppColors.Success
                    "current" -> AppColors.TextPrimary
                    else -> AppColors.TextHint
                }
            )

            if (!displayDescription.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = displayDescription,
                    fontSize = 12.sp,
                    color = AppColors.TextSecond,
                    lineHeight = 18.sp
                )
            }
        }

        if (displayDate.isNotEmpty()) {
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = displayDate,
                fontSize = 12.sp,
                color = if (stepStatus == "completed") AppColors.Success.copy(alpha = 0.7f) else AppColors.TextHint,
                modifier = Modifier.padding(top = 2.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.End
            )
        }
    }
}

