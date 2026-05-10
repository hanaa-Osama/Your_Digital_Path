package com.example.yourdigitalpath.presentation.orders_history.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
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
    onOrderClick: (orderId: String) -> Unit,
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val orders by viewModel.orders.collectAsState()
    val selectedStatus by viewModel.selectedStatus.collectAsState()

    val primaryColor = AppColors.Primary
    val warningColor = AppColors.Warning
    val warningBgColor = AppColors.WarningBg
    val successColor = AppColors.Success
    val successBgColor = AppColors.SuccessBg

    val filterOptions =
        remember(primaryColor, warningColor, warningBgColor, successColor, successBgColor) {
            listOf(
                FilterOption(null, "الكل", Color.White, primaryColor),
                FilterOption(OrderStatus.InProgress, "جاري", warningColor, warningBgColor),
                FilterOption(OrderStatus.Completed, "مكتمل", successColor, successBgColor),
            )
        }


    Scaffold(
        containerColor = Color(0xFFF9FAFB),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(primaryColor, Color(0xFF293241))
                        )
                    )
                    .padding(horizontal = 24.dp, vertical = 24.dp)
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text(
                            text = "تتبع طلباتك",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                        Text(
                            text = "لديك ${orders.size} طلبات نشطة في حسابك",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
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
                                fontSize = 14.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = option.selectedBgColor,
                            selectedLabelColor = option.selectedTextColor,
                            containerColor = Color.White,
                            labelColor = Color(0xFF667085)
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = Color(0xFFEAECF0),
                            selectedBorderColor = option.selectedBgColor,
                            borderWidth = 1.dp
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(40.dp)
                    )
                }
            }

            if (orders.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "لا توجد طلبات تطابق الفلتر",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF667085)
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = orders,
                        key = { it.id }
                    ) { order ->
                        OrderCard(
                            orderModel = order,
                            onClick = { onOrderClick(order.id) }
                        )
                    }
                }
            }
        }
    }
}

