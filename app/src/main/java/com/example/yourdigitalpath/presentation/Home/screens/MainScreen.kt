package com.example.yourdigitalpath.presentation.Home.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.presentation.viewModel.ProfileViewModel
import com.example.yourdigitalpath.ui.theme.AppColors
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.yourdigitalpath.domain.model.OrderModel
import com.example.yourdigitalpath.domain.model.eventsList
import com.example.yourdigitalpath.presentation.Home.components.EventSection
import com.example.yourdigitalpath.presentation.Home.components.HeaderSection

@Composable
fun MainScreen(
    onBack: () -> Unit,
    navController: NavController,
    userName: String
) {
    val servicesCount = remember { eventsList().size }
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val ordersViewModel: com.example.yourdigitalpath.presentation.orders_history.OrdersViewModel = hiltViewModel()
    val appSettings by profileViewModel.appSettings.collectAsState()
    val ordersList by ordersViewModel.orders.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        HeaderSection(
            userName = userName,
            servicesCount = servicesCount
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            EventSection(navController, ordersList)
        }
    }
}
