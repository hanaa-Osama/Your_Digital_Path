package com.blqes.digi.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.yourdigitalpath.R

val primary = Color(0xFF3D5A80)

@Composable
fun BottomNavBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        NavigationBar(
            modifier = Modifier.background(Color.White)
        ) {
            NavigationBarItem(
                selected = currentRoute == "home_screen",
                onClick = {
                    if (currentRoute != "home_screen") {
                        navController.navigate("home_screen") {
                            popUpTo("home_screen") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
                icon = { Icon(Icons.Default.Home, contentDescription = null, tint = primary) },
                label = { Text("الرئيسية", color = primary) }
            )

            NavigationBarItem(
                selected = currentRoute?.startsWith("service_request_screen") == true,
                onClick = {},
                icon = {
                    val isSelected = currentRoute?.startsWith("service_request_screen") == true
                    Icon(
                        painter = painterResource(R.drawable.file),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (isSelected) primary else Color.Gray
                    )
                },
                label = {
                    val isSelected = currentRoute?.startsWith("service_request_screen") == true
                    Text(
                        "طلباتي",
                        color = if (isSelected) primary else Color.Gray
                    )
                }
            )

            NavigationBarItem(
                selected = false,
                onClick = {
                    navController.navigate("notifications_screen")
                },
                icon = {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = null,
                        tint = primary,
                    )
                },
                label = { Text("إشعارات", color = primary) }
            )

            NavigationBarItem(
                selected = false,
                onClick = {},
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
                label = { Text("حسابي", color = Color.Gray) }
            )
        }
    }
}

@Composable
@Preview
private fun BottomNavBarPrev() {
    BottomNavBar(navController = rememberNavController())
}