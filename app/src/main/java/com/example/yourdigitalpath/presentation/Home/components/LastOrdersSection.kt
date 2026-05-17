package com.example.yourdigitalpath.presentation.Home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.yourdigitalpath.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yourdigitalpath.ui.theme.AppColors
import com.example.yourdigitalpath.domain.model.OrderModel
import com.example.yourdigitalpath.presentation.orders_history.order_component.OrderCard as HistoryOrderCard
@Composable
fun LastOrdersSection(ordersList: List<OrderModel> = emptyList()) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.last_orders),
            style = MaterialTheme.typography.titleMedium,
            color = AppColors.TextPrimary
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (ordersList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_orders_yet),
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.TextHint,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        } else {
            ordersList.take(2).forEach { order ->
                HistoryOrderCard(
                    orderModel = order,
                    onClick = { /* Handle navigation if needed */ }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
@Preview
private fun LastOrdersSectionPrev() {
    LastOrdersSection()
}