package com.example.yourdigitalpath.presentation.orders_history.order_component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.domain.model.OrderModel
import com.example.yourdigitalpath.domain.model.OrderStatus
import com.example.yourdigitalpath.ui.theme.AppColors
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.ui.res.stringResource
import com.example.yourdigitalpath.R

@Composable
fun OrderCard(
    orderModel: OrderModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormatted = remember(orderModel.requestDate) {
        SimpleDateFormat("d MMMM yyyy", Locale("ar")).format(Date(orderModel.requestDate))
    }
    val displayTitle =
        if (orderModel.serviceName.contains("طلب")) {
            orderModel.serviceName
        } else {
            stringResource(
                R.string.extract_request,
                orderModel.serviceName
            )
        }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(AppColors.Primary.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        ServiceIconBadge(serviceName = orderModel.serviceName)
                    }

                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .padding(horizontal = 14.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = displayTitle,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF293241)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stringResource(
                                R.string.order_number1,
                                orderModel.id
                            ),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    StatusChip(status = orderModel.status)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = dateFormatted,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )

                    if (orderModel.status is OrderStatus.InProgress) {
                        Text(
                            text = stringResource(
                                R.string.processing_progress,
                                orderModel.progressPercent
                            ),
                            fontSize = 12.sp,
                            color = AppColors.Primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (orderModel.status is OrderStatus.InProgress && orderModel.progressPercent > 0) {
                    Spacer(modifier = Modifier.height(10.dp))
                    LinearProgressIndicator(
                        progress = { orderModel.progressPercent.toFloat() / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        color = AppColors.Primary,
                        trackColor = AppColors.Primary.copy(alpha = 0.1f)
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true, locale = "ar")
//@Composable
//fun PreviewOrderCard() {
//    Column(
//        modifier = Modifier
//            .padding(16.dp)
//            .fillMaxWidth(),
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        OrderCard(
//            orderModel = OrderModel(
//                id = "REQ-2025-00841",
//                serviceName = "تجديد بطاقة الهوية",
//                requestDate = System.currentTimeMillis(),
//                status = OrderStatus.InProgress,
//                progressPercent = 45,
//                totalFee = 100,
//                copiesCount = 1,
//                deliveryMethod = "البريد"
//            )
//        )
//
//        OrderCard(
//            orderModel = OrderModel(
//                id = "REQ-2025-00838",
//                serviceName = "شهادة ميلاد",
//                requestDate = System.currentTimeMillis() - 864000000,
//                status = OrderStatus.Completed,
//                progressPercent = 100,
//                totalFee = 50,
//                copiesCount = 1,
//                deliveryMethod = "استلام يدوي"
//            )
//        )
//    }
//}