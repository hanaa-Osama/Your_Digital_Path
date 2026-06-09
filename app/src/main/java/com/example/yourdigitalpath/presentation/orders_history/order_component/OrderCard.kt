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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.domain.model.OrderModel
import com.example.yourdigitalpath.domain.model.OrderStatus
import com.example.yourdigitalpath.ui.components.getLocalizedType
import com.example.yourdigitalpath.ui.components.getServiceTitle
import com.example.yourdigitalpath.ui.theme.AppColors
import com.example.yourdigitalpath.ui.theme.DateUtils

@Composable
fun OrderCard(
    orderModel: OrderModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormatted = remember(orderModel.requestDate) {
        DateUtils.formatOrderDate(orderModel.requestDate)
    }
    val requestKeyword = stringResource(R.string.request_keyword)

    val serviceNameParts = orderModel.serviceName.split(" - ")
    val rawPart1 = if (serviceNameParts.isNotEmpty()) serviceNameParts[0].trim() else ""
    val rawPart2 = if (serviceNameParts.size > 1) serviceNameParts[1].trim() else ""

    val mainService = getServiceTitle(rawPart1)
    val subService = getLocalizedType(rawPart2)

    val displayTitle = if (subService.isNotEmpty() && mainService.isNotEmpty() && mainService != subService) {
        val typePart = stringResource(R.string.extract_request, subService)
        "$typePart - $mainService"
    } else if (mainService.isNotEmpty()) {
        if (mainService.contains(requestKeyword, true)) mainService
        else stringResource(R.string.extract_request, mainService)
    } else if (subService.isNotEmpty()) {
        stringResource(R.string.extract_request, subService)
    } else {
        orderModel.serviceName
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
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
                            color = AppColors.TextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stringResource(
                                R.string.order_number1,
                                orderModel.id
                            ),
                            fontSize = 12.sp,
                            color = AppColors.TextHint
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
                        color = AppColors.TextHint,
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
