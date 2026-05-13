package com.blqes.digi.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.yourdigitalpath.R

val primary = Color(0xFF3D5A80)

@Composable
fun BottomNavBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val configuration = LocalConfiguration.current
    val isArabic = configuration.locales[0].language == "ar"
    val layoutDirection = if (isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(
        LocalLayoutDirection provides layoutDirection
    ) {
        NavigationBar(
            containerColor = Color.White
        ) {
            val items = listOf(
                BottomNavItem(
                    stringResource(R.string.home),
                    Icons.Default.Home,
                    "home_screen"
                ),

                BottomNavItem(
                    stringResource(R.string.my_orders),
                    Icons.Default.InsertDriveFile,
                    "my_orders_screen"
                ),

                BottomNavItem(
                    stringResource(R.string.notifications),
                    Icons.Default.Notifications,
                    "notifications_screen"
                ),

                BottomNavItem(
                    stringResource(R.string.my_account),
                    Icons.Default.Person,
                    "profile_screen"
                )
            )

            items.forEach { item ->
                val isSelected = currentRoute == item.route ||
                        (item.route == "my_orders_screen" && (
                                currentRoute?.startsWith("service_request_screen") == true ||
                                        currentRoute?.startsWith("data_entry_screen") == true ||
                                        currentRoute == "file_upload_screen"||
                                        currentRoute == "my_orders_screen"
                                ))

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        if (item.route == "home_screen") {
                            navController.navigate("home_screen") {
                                popUpTo(0)
                                launchSingleTop = true
                            }
                        } else {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (isSelected) primary else Color.LightGray
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            color = if (isSelected) primary else Color.LightGray
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(

                        indicatorColor = Color.Transparent 
                    )
                )
            }
        }
    }
}

private data class BottomNavItem(
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val route: String
)
