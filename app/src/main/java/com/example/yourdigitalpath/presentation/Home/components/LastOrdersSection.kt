package com.example.yourdigitalpath.presentation.Home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.yourdigitalpath.R
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.blqes.digi.ui.components.OrderCard
import com.example.yourdigitalpath.domain.model.Order
import com.example.yourdigitalpath.domain.model.OrderStatus
import com.example.yourdigitalpath.ui.theme.formatOrderDate

@Composable
fun LastOrdersSection() {
    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.last_orders),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(12.dp))

            val orders = listOf(
                Order(
                    icon = Icons.Default.Person,
                    title = stringResource(R.string.birth_certificate),
                    date = formatOrderDate(
                        1743552000000
                    ),
                    status = OrderStatus.Completed
                ),
                Order(
                    icon = Icons.Default.Notifications,
                    title = stringResource(R.string.renew_id),
                    date = formatOrderDate(
                        1742428800000
                    ),
                    status = OrderStatus.InProgress
                )
            )

            orders.forEach { order ->
                OrderCard(order)
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