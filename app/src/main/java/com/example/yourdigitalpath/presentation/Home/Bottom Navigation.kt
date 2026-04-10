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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// هنا تعريف اللون
val primary = Color(0xFF3D5A80)

@Composable
fun BottomNavBar(
    navController: NavController
) {
    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        NavigationBar(
            modifier = Modifier.background(Color.White)
        ) {
            NavigationBarItem(
                selected = false,
                onClick = {
                    navController.navigate("home_screen")
                },
                icon = { Icon(Icons.Default.Home, contentDescription = null, tint = primary) },
                label = { Text("الرئيسية", color = primary) }
            )

            NavigationBarItem(
                selected = false,
                onClick = {},
                icon = { Icon(Icons.Default.Lock, contentDescription = null, tint = primary) },
                label = { Text("طلباتي", color = primary) }
            )

            NavigationBarItem(
                selected = false,
                onClick = {
                    navController.navigate("notifications_screen")
                },
                icon = { Icon(Icons.Default.Person, contentDescription = null, tint = primary) },
                label = { Text("إشعارات", color = primary) }
            )

            NavigationBarItem(
                selected = false,
                onClick = {},
                icon = { Icon(Icons.Default.Person, contentDescription = null, tint = primary) },
                label = { Text("حسابي", color = primary) }
            )
        }
    }
}

@Composable
@Preview
private fun BottomNavBarPrev() {
    BottomNavBar(navController = rememberNavController())
}