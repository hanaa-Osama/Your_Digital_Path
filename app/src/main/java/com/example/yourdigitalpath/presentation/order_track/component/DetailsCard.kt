package com.example.yourdigitalpath.presentation.order_track.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.yourdigitalpath.domain.model.OrderTrackingDetail

@Composable
fun DetailsCard(
    currentOrder: OrderTrackingDetail?,
    orderId: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFEAECF0))
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            DetailRow("رقم الطلب", currentOrder?.orderId ?: orderId)
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color(0xFFF2F4F7)
            )
            DetailRow("نوع الخدمة", currentOrder?.serviceType ?: " لم يتم تحديد نوع الخدمة ")
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color(0xFFF2F4F7)
            )
            DetailRow("تاريخ التقديم", currentOrder?.date ?: " لم يتم التحديد ")
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color(0xFFF2F4F7)
            )
            DetailRow("طريقة الاستلام", currentOrder?.deliveryMethod ?: " لم يتم التحديد ")
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color(0xFFF2F4F7)
            )
            DetailRow(
                "المبلغ المدفوع",
                currentOrder?.price ?: "  لم يتم التحديد ",
                valueColor = Color(0xFF067647)
            )
        }
    }
}