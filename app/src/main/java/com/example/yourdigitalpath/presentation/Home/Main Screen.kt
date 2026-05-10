package com.example.yourdigitalpath.presentation.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.blqes.digi.presentation.HeaderSection
import com.example.yourdigitalpath.domain.model.OrderModel

@Composable
fun MainScreen(
    onBack: () -> Unit,
    navController: NavController,
    userName: String,
    ordersList: List<OrderModel> = emptyList()
) {
    val servicesCount = remember { getEvents().size }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF3D5A80))
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
            EventSection(navController)
        }
    }
}
