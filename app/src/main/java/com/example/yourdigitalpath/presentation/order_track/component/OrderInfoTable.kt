package com.example.yourdigitalpath.presentation.order_track.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.domain.model.OrderTrackingDetail

@Composable
fun OrderInfoTable(detail: OrderTrackingDetail) {
    Card(shape = RoundedCornerShape(16.dp)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            InfoRow(
                stringResource(R.string.order_number),
                detail.orderId
            )
            InfoRow(
                stringResource(R.string.service_type),
                detail.serviceType
            )
            InfoRow(
                stringResource(R.string.submission_date),
                detail.date
            )
            InfoRow(
                stringResource(R.string.paid_amount),
                detail.price,
                isAmount = true
            )
        }
    }
}