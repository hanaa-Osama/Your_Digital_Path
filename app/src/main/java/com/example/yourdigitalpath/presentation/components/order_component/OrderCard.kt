package com.example.yourdigitalpath.presentation.components.order_component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.domain.model.OrderModel
import com.example.yourdigitalpath.domain.model.OrderStatus
import com.example.yourdigitalpath.ui.theme.AppColors
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun OrderCard(
    orderModel: OrderModel,
    modifier: Modifier = Modifier
) {
    val dateFormatted = remember(orderModel.requestDate) {
        SimpleDateFormat("d MMMM yyyy", Locale("ar")).format(Date(orderModel.requestDate))
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(0.5.dp, AppColors.Border, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface)
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    ServiceIconBadge(serviceName = orderModel.serviceName)

                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        Text(
                            text = orderModel.serviceName,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppColors.TextPrimary
                        )
                        Text(
                            text = orderModel.id,
                            fontSize = 11.sp,
                            color = AppColors.TextHint
                        )
                        Text(
                            text = dateFormatted,
                            fontSize = 11.sp,
                            color = AppColors.TextHint
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    StatusChip(status = orderModel.status)
                }

                if (orderModel.status is OrderStatus.InProgress && orderModel.progressPercent > 0) {
                    Spacer(modifier = Modifier.height(16.dp))

                    LinearProgressIndicator(
                        progress = { orderModel.progressPercent / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        color = AppColors.Primary,
                        trackColor = AppColors.Border
                    )
                    Text(
                        text = "مراجعة المستندات — ${orderModel.progressPercent}%",
                        fontSize = 11.sp,
                        color = AppColors.TextHint,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                }
            }
        }
    }
}

@Preview(showBackground = true, locale = "ar")
@Composable
fun PreviewOrderCard() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        //  في حالة جاري التنفيذ
        OrderCard(
            orderModel = OrderModel(
                id = "REQ-2025-00841",
                serviceName = "تجديد بطاقة الهوية",
                requestDate = System.currentTimeMillis(),
                status = OrderStatus.InProgress,
                progressPercent = 45,
                totalFee = 100,
                copiesCount = 1,
                deliveryMethod = "البريد"
            )
        )

        //  في حالة "مكتمل"
        OrderCard(
            orderModel = OrderModel(
                id = "REQ-2025-00838",
                serviceName = "شهادة ميلاد",
                requestDate = System.currentTimeMillis() - 864000000,
                status = OrderStatus.Completed,
                progressPercent = 100,
                totalFee = 50,
                copiesCount = 1,
                deliveryMethod = "استلام يدوي"
            )
        )
    }
}