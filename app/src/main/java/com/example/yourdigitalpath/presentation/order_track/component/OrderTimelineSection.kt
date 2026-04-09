package com.example.yourdigitalpath.presentation.order_track.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import com.example.yourdigitalpath.ui.theme.PrimaryBlue
import com.example.yourdigitalpath.ui.theme.SuccessGreen

@Composable
fun OrderTimelineSection(steps: List<TrackingStep>) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F4F7).copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "تتبع حالة الطلب",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF1D2939),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right
            )
            Spacer(modifier = Modifier.height(24.dp))

            // جوه OrderTimelineSection.kt
            steps.forEachIndexed { index, step ->
                TimelineItem(
                    status = step.title,
                    date = step.timestamp,
                    stepStatus = step.status, // بنباصي الحالة دي علطول
                    isLast = index == steps.size - 1
                )
                // مسافة بسيطة بين كل خطوة والتانية
                if (index != steps.size - 1) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
@Composable
fun TimelineItem(
    status: String, // "تم استلام الطلب"، "قيد المراجعة"...
    date: String, // "5 أبريل"، "الآن"، "قريباً"...
    stepStatus: String, // completed, current, or pending
    isLast: Boolean // عشان نعرف نوقف الخط ولا لأ
) {
    // 1. نحدد الألوان بناءً على حالة الخطوة
    val successColor = SuccessGreen // اللي إنتي معرفاه
    val currentColor = PrimaryBlue // الأزرق الغامق
    val pendingColor = Color(0xFFF2F4F7) // الرمادي الفاتح للنقطة
    val lineColor = Color(0xFFEAECF0) // الرمادي الفاتح جداً للخط

    val dateColor =
        if (stepStatus == "completed" || stepStatus == "current") Color.Gray else Color.LightGray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.End // عشان الكلام يبقى يمين
    ) {
        // --- النص (على اليمين) ---
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)
        ) {
            Text(
                text = status,
                fontSize = 14.sp,
                fontWeight = if (stepStatus == "current") FontWeight.Bold else FontWeight.Medium,
                color = when (stepStatus) {
                    "completed" -> successColor
                    "current" -> currentColor
                    else -> Color.LightGray // لسه مجاش
                },
                textAlign = TextAlign.Right
            )
            Text(
                text = date,
                fontSize = 12.sp,
                color = dateColor,
                textAlign = TextAlign.Right
            )
        }

        // --- النقطة والخط (على اليسار) ---
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(24.dp)
        ) {
            // النقطة
            if (stepStatus == "completed") {
                // نقطة خضراء جواها علامة صح
                Surface(
                    shape = CircleShape,
                    color = successColor,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            } else if (stepStatus == "current") {
                // نقطة بيضاء بكنار أزرق غامق
                Surface(
                    shape = CircleShape,
                    border = BorderStroke(2.dp, currentColor),
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                ) {}
            } else {
                // نقطة رمادي فاتح (Pending)
                Surface(
                    shape = CircleShape,
                    color = pendingColor,
                    modifier = Modifier.size(24.dp)
                ) {}
            }

            // الخط
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .weight(1f) // بيملا المساحة بين النقط
                        .padding(vertical = 4.dp)
                        .background(
                            if (stepStatus == "completed") successColor else lineColor
                        )
                )
            } else {
                // لو آخر واحد، بنحط مساحة فاضية عشان الـ UI يتظبط
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}