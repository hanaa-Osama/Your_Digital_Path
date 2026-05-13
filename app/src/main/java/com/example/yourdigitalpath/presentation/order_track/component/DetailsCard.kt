package com.example.yourdigitalpath.presentation.order_track.component

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
import androidx.compose.ui.res.stringResource
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.domain.model.OrderTrackingDetail
import com.example.yourdigitalpath.ui.theme.AppColors

@Composable
fun DetailsCard(
    currentOrder: OrderTrackingDetail?,
    orderId: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 12.dp, horizontal = 4.dp)) {
            DetailRow(
                stringResource(R.string.order_number),
                currentOrder?.orderId ?: orderId
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color(0xFFF2F4F7)
            )
            DetailRow(
                stringResource(R.string.service_type),
                currentOrder?.serviceType
                    ?: stringResource(R.string.service_not_specified)
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color(0xFFF2F4F7)
            )
            DetailRow(
                stringResource(R.string.submission_date),
                currentOrder?.date
                    ?: stringResource(R.string.not_specified)
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color(0xFFF2F4F7)
            )
            DetailRow(
                stringResource(R.string.delivery_method),
                currentOrder?.deliveryMethod
                    ?: stringResource(R.string.not_specified)
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color(0xFFF2F4F7)
            )
            DetailRow(
                stringResource(R.string.paid_amount),
                currentOrder?.price ?: stringResource(R.string.not_specified),
                valueColor = AppColors.Success
            )
        }
    }
}
