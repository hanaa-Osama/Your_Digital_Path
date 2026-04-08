package com.example.yourdigitalpath.presentation.tracking.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.domain.model.TrackingStep
import com.example.yourdigitalpath.ui.theme.PrimaryBlue
import com.example.yourdigitalpath.ui.theme.SuccessGreen

@Composable
fun OrderTimelineSection(steps: List<TrackingStep>) {
    Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "مسار الطلب",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right
            )
            Spacer(modifier = Modifier.height(16.dp))

            // اللف على الخطوات
            steps.forEachIndexed { index, step ->
                TimelineItem(
                    status = step.title,
                    date = step.timestamp,
                    // اللوجيك بتاع الحالة:
                    isDone = step.status == "completed",
                    isCurrent = step.status == "active",
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
    isDone: Boolean,
    isCurrent: Boolean = false,
    isLast: Boolean

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            // تعديل بسيط في الـ drawBehind داخل TimelineItem
            .drawBehind {
                if (!isLast) {
                    // حساب السنتر بتاع الدائرة تقريباً عشان الخط يبدأ منها
                    val circleCenterOffset = 45f // ده الرقم اللي إنتِ مستخدماه
                    drawLine(
                        color = if (isDone) SuccessGreen else Color.LightGray,
                        start = Offset(size.width - circleCenterOffset, 60f), // بداية من نص الدائرة
                        end = Offset(size.width - circleCenterOffset, size.height + 40f),
                        strokeWidth = 2.dp.toPx()
                    )
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Column(horizontalAlignment = Alignment.End, modifier = Modifier.weight(1f)) {
            Text(
                status,
                fontSize = 14.sp,
                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                color = if (isCurrent) PrimaryBlue else Color.Gray
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(date, fontSize = 12.sp, color = Color.LightGray)
        Spacer(modifier = Modifier.width(16.dp))

        // الدائرة
        Surface(
            shape = CircleShape,
            border = BorderStroke(
                2.dp,
                if (isDone || isCurrent) (if (isDone) SuccessGreen else PrimaryBlue) else Color.LightGray
            ),
            color = if (isDone) SuccessGreen else Color.White,
            modifier = Modifier.size(24.dp)
        ) {
            if (isDone) Icon(
                Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}