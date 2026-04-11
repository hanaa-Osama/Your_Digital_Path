package com.example.yourdigitalpath.presentation.orders_history.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.domain.model.OrderStatus
import com.example.yourdigitalpath.presentation.orders_history.OrdersViewModel
import com.example.yourdigitalpath.presentation.orders_history.order_component.OrderCard
import com.example.yourdigitalpath.ui.theme.AppColors

private data class FilterOption(
    val status: OrderStatus?,
    val label: String,
    val selectedTextColor: Color,
    val selectedBgColor: Color,
)

@Composable
fun MyOrdersScreen(
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val orders by viewModel.orders.collectAsState()
    val selectedStatus by viewModel.selectedStatus.collectAsState()

    val filterOptions = listOf(
        FilterOption(null,                   "الكل",    Color.White,          AppColors.Primary),
        FilterOption(OrderStatus.InProgress, "جاري",    AppColors.Warning,    AppColors.WarningBg),
        FilterOption(OrderStatus.Completed,  "مكتمل",   AppColors.Success,    AppColors.SuccessBg),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.Primary)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    Text(
                        text = "طلباتي",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "${orders.size} طلبات إجمالاً",
                        fontSize = 12.sp,
                        color = AppColors.Surface.copy(alpha = 0.7f),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }

        HorizontalDivider(color = AppColors.Border, thickness = 0.5.dp)

        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.End),
            reverseLayout = true
        ) {
            items(filterOptions) { option ->
                val isSelected = selectedStatus == option.status

                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.onStatusFilterChanged(option.status) },
                    label = {
                        Text(
                            text = option.label,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = option.selectedBgColor,
                        selectedLabelColor     = option.selectedTextColor,
                        containerColor = AppColors.Surface,
                        labelColor     = AppColors.TextSecond
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled             = true,
                        selected            = isSelected,
                        borderColor         = AppColors.Border,
                        selectedBorderColor = option.selectedBgColor,
                        borderWidth         = 0.5.dp,
                        selectedBorderWidth = 0.5.dp
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
            }
        }

        if (orders.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "لا يوجد طلبات",
                    fontSize = 14.sp,
                    color = AppColors.TextHint
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    items = orders,
                    key = { it.id }
                ) { order ->
                    OrderCard(orderModel = order)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMyOrdersScreen() {
    MyOrdersScreen()
}