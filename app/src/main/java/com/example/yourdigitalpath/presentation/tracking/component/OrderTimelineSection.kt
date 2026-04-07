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
import com.example.yourdigitalpath.ui.theme.PrimaryBlue
import com.example.yourdigitalpath.ui.theme.SuccessGreen

@Composable
fun OrderTimelineSection() {
    Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "مسار الطلب",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right
            )
            Spacer(modifier = Modifier.height(16.dp))

            TimelineItem("تم استلام الطلب", "2 أبريل", true, isLast = false)
            TimelineItem("تأكيد الدفع", "2 أبريل", true, isLast = false)
            TimelineItem("مراجعة المستندات", "جاري", false, isLast = false, isCurrent = true)
            TimelineItem("إصدار الوثيقة", "-", false, isLast = false)
            TimelineItem("التسليم", "-", false, isLast = true)
        }
    }
}

@Composable
fun TimelineItem(
    status: String,
    date: String,
    isDone: Boolean,
    isLast: Boolean,
    isCurrent: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .drawBehind {
                if (!isLast) {
                    drawLine(

                        color = if (isDone) SuccessGreen else Color.LightGray,
                        start = Offset(size.width - 45f, 70f),
                        end = Offset(size.width - 45f, size.height + 30f),
                        strokeWidth = 3.dp.toPx()
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