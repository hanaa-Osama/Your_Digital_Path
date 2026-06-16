package com.example.yourdigitalpath.presentation.order_track.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.domain.model.OrderTrackingDetail
import com.example.yourdigitalpath.ui.theme.AppColors

@Composable
fun StatusHighlightCard(
    currentOrder: OrderTrackingDetail?
) {
    val lastStep = currentOrder?.steps
        ?.findLast { it.status == "current" || it.status == "completed" }
    val isStepCompleted = lastStep?.status == "completed"

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            if (isStepCompleted) AppColors.SuccessBg else AppColors.WarningBg,
                            AppColors.Surface
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(
                            R.string.current_order_status
                        ),
                        color = AppColors.TextSecond,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = lastStep?.let {
                            if (it.status == "completed") {
                                when (it.title) {
                                    "قيد المراجعة" -> "تم المراجعة"
                                    else -> if (it.title.startsWith("جاري ")) {
                                        it.title.replaceFirst("جاري ", "تم ")
                                    } else it.title
                                }
                            } else it.title
                        } ?: stringResource(R.string.under_review),
                        color = if (isStepCompleted) AppColors.Success else AppColors.Warning,
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            (if (isStepCompleted) AppColors.Success else AppColors.Warning).copy(
                                alpha = 0.1f
                            ),
                            RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        if (isStepCompleted) Icons.Default.Check else Icons.Outlined.AccessTime,
                        contentDescription = null,
                        tint = if (isStepCompleted) AppColors.Success else AppColors.Warning,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val progress = (currentOrder?.progressPercent ?: 0).toFloat() / 100f
            androidx.compose.material3.LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = if (isStepCompleted) AppColors.Success else AppColors.Warning,
                trackColor = (if (isStepCompleted) AppColors.Success else AppColors.Warning).copy(
                    alpha = 0.1f
                ),
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(
                        R.string.completed_percentage,
                        currentOrder?.progressPercent ?: 0
                    ),
                    color = if (isStepCompleted) AppColors.Success else AppColors.Warning,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    stringResource(
                        R.string.documents_review
                    ),
                    color = AppColors.TextSecond,
                    fontSize = 12.sp
                )
            }
        }
    }
}
