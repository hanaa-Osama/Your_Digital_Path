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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.domain.model.OrderStatus
import com.example.yourdigitalpath.presentation.orders_history.OrdersViewModel
import com.example.yourdigitalpath.presentation.orders_history.order_component.OrderCard
import com.example.yourdigitalpath.ui.theme.AppColors
import com.example.yourdigitalpath.ui.theme.LocalDarkTheme

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
    val isDarkMode = LocalDarkTheme.current

    val allText = stringResource(R.string.all)
    val inProgressText = stringResource(R.string.inProgress)
    val completedText = stringResource(R.string.completed)

    val primary = AppColors.Primary
    val primaryLight = AppColors.PrimaryLight
    val warning = AppColors.Warning
    val warningBg = AppColors.WarningBg
    val success = AppColors.Success
    val successBg = AppColors.SuccessBg

    val filterOptions = remember(allText, inProgressText, completedText, primary, primaryLight, warning, warningBg, success, successBg) {
        listOf(
            FilterOption(null, allText, primary, primaryLight),
            FilterOption(OrderStatus.InProgress, inProgressText, warning, warningBg),
            FilterOption(OrderStatus.Completed, completedText, success, successBg),
        )
    }

    val headerGradientColors = if (isDarkMode) {
        listOf(Color(0xFF1D2A44), Color(0xFF0F1929))
    } else {
        listOf(primary, Color(0xFF293241))
    }

    Scaffold(
        containerColor = AppColors.Background,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = Brush.verticalGradient(colors = headerGradientColors))
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.track_your_orders),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = stringResource(R.string.active_orders_count, orders.size),
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 4.dp)
                    )
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
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
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
                            containerColor = AppColors.Surface,
                            labelColor = AppColors.TextSecond
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = AppColors.Border,
                            selectedBorderColor = option.selectedTextColor,
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
                    Text(
                        text = stringResource(R.string.no_orders_match_filter),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = AppColors.TextSecond
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items = orders, key = { it.id }) { order ->
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
