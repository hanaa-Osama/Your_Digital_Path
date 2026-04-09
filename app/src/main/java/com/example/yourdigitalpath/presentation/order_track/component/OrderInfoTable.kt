package com.example.yourdigitalpath.presentation.order_track.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yourdigitalpath.domain.model.OrderTrackingDetail

@Composable
fun OrderInfoTable(detail: OrderTrackingDetail) {
    Card(shape = RoundedCornerShape(16.dp)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            InfoRow("رقم الطلب", detail.orderId)
            InfoRow("نوع الخدمة", detail.serviceType)
            InfoRow("تاريخ التقديم", detail.date)
            InfoRow("المبلغ المدفوع", detail.price, isAmount = true)
        }
    }
}