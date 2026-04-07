package com.example.yourdigitalpath.presentation.tracking.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yourdigitalpath.ui.theme.DividerColor

@Composable
fun OrderInfoTable() {
    Card(shape = RoundedCornerShape(16.dp)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            InfoRow("رقم الطلب", "REQ-2025-00841")
            Divider(color = DividerColor)
            InfoRow("نوع الخدمة", "تجديد بطاقة الهوية")
            Divider(color = DividerColor)
            InfoRow("تاريخ التقديم", "2 أبريل 2025")
            Divider(color = DividerColor)
            InfoRow("طريقة الاستلام", "توصيل للمنزل")
            Divider(color = DividerColor)
            InfoRow("المبلغ المدفوع", "35 جنيه", isAmount = true)
        }
    }
}