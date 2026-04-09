package com.example.yourdigitalpath.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.presentation.service_request.DataScreen
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestStep
import com.example.yourdigitalpath.ui.components.BackgroundGray
import com.example.yourdigitalpath.ui.components.PrimaryBlue
import com.example.yourdigitalpath.ui.components.StepperComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceRequestScreen(
    onBack: () -> Unit = {}
) {
    var currentStep by remember { mutableIntStateOf(1) }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                Surface(shadowElevation = 0.dp) {
                    TopAppBar(
                        title = {
                            Column(horizontalAlignment = Alignment.Start) {
                                Text(
                                    text = "شهادة الميلاد",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = "حدد نوع الطلب",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                if (currentStep > 1) {
                                    currentStep--
                                } else {
                                    onBack()
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = "Back",
                                    tint = PrimaryBlue
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.White
                        )
                    )
                }
            },
            bottomBar = {
                NavigationBar(
                    containerColor = Color.White,
                    tonalElevation = 8.dp
                ) {
                    NavigationBarItem(
                        selected = false,
                        onClick = { },
                        icon = { Icon(Icons.Outlined.Home, contentDescription = null) },
                        label = { Text("الرئيسية", fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { },
                        icon = { Icon(Icons.Outlined.Notifications, contentDescription = null) },
                        label = { Text("إشعارات", fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                    NavigationBarItem(
                        selected = true,
                        onClick = { },
                        icon = { Icon(Icons.Outlined.Home, contentDescription = null) },
                        label = { Text("طلباتي", fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryBlue,
                            selectedTextColor = PrimaryBlue,
                            indicatorColor = Color.Transparent
                        )
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { },
                        icon = { Icon(Icons.Outlined.Person, contentDescription = null) },
                        label = { Text("حسابي", fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
            },
            containerColor = BackgroundGray
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.Start
            ) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.LightGray.copy(alpha = 0.3f)
                )

                StepperComponent(currentStep = currentStep)

                Box(modifier = Modifier.weight(1f)) {
                    when (currentStep) {
                        1 -> ServiceRequestStep(
                            onNext = { currentStep = 2 }
                        )

                        2 -> DataScreen()
                        else -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Step $currentStep")
                            }
                        }
                    }
                }
            }
        }
    }
}
