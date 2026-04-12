package com.example.yourdigitalpath.presentation.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.blqes.digi.presentation.HeaderSection
import com.example.yourdigitalpath.domain.model.OrderModel

@Composable
fun MainScreen(
    onBack: () -> Unit,
    navController: NavController,
    userName: String = "هناء اسامة",
    ordersList: List<OrderModel> = emptyList()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF3D5A80))
    ) {
        HeaderSection(
            userName = userName,
            orders = ordersList
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            EventSection(navController)
        }
    }
}

@Composable
@Preview
private fun MainScreenPreview() {
    MainScreen(
        navController = rememberNavController(),
        onBack = {},
        userName = "هناء اسامة",
        ordersList = emptyList()
    )
}