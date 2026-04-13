package com.example.yourdigitalpath.Routes

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.blqes.digi.presentation.personalscreen.PersonalDataScreen
import com.example.yourdigitalpath.presentation.Home.MainScreen
import com.example.yourdigitalpath.presentation.data_entry.DataScreen
import com.example.yourdigitalpath.presentation.notification.NotificationViewModel
import com.example.yourdigitalpath.presentation.notification.screen.NotificationsScreen
import com.example.yourdigitalpath.presentation.orders_history.screens.MyOrdersScreen
import com.example.yourdigitalpath.presentation.personal_screen.AccountDataScreen
import com.example.yourdigitalpath.presentation.profile.screens.EditProfileScreen
import com.example.yourdigitalpath.presentation.profile.screens.NotificationsSettingScreen
import com.example.yourdigitalpath.presentation.profile.screens.ProfileScreen
import com.example.yourdigitalpath.presentation.profile.screens.SecurityScreen
import com.example.yourdigitalpath.presentation.profile.screens.SettingsScreen
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestScreen
import com.example.yourdigitalpath.presentation.welcom_screen.LoginScreen
import com.example.yourdigitalpath.presentation.welcom_screen.WelcomeScreen

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome_screen"
    ) {

        composable("welcome_screen") {
            WelcomeScreen(navController)
        }

        composable("login_screen") {
            LoginScreen(navController)
        }

        composable("register_screen") {
            PersonalDataScreen(
                onNext = {
                    navController.navigate("account_data_screen")
                }
            )
        }

        composable("home_screen") {
            MainScreen(
                navController = navController,
                onBack = {},
                userName = ""
            )
        }

        composable(
            "service_request_screen/{serviceName}",
            arguments = listOf(navArgument("serviceName") { type = NavType.StringType })
        ) { backStackEntry ->
            val serviceName = backStackEntry.arguments?.getString("serviceName") ?: ""
            ServiceRequestScreen(
                serviceName = serviceName,
                navController = navController,
                onNext = { navController.navigate("data_entry_screen/$serviceName") },
                onBack = { navController.popBackStack() }
            )
        }

        composable("account_data_screen") {
            AccountDataScreen(
                onBack = { navController.popBackStack() },
                onRegister = { navController.navigate("home_screen") {
                    popUpTo(0) { inclusive = true }
                }}
            )
        }

        composable(
            "data_entry_screen/{serviceName}",
            arguments = listOf(navArgument("serviceName") { type = NavType.StringType })
        ) {
            DataScreen(
                serviceName = it.arguments?.getString("serviceName") ?: "",
                onNext = { navController.navigate("file_upload_screen") },
                onBack = { navController.popBackStack() }
            )
        }

        composable("notifications_screen") {
            val viewModel: NotificationViewModel = hiltViewModel()
            NotificationsScreen(
                onBack = { navController.popBackStack() },
                notificationViewModel = viewModel,
                navController = navController
            )
        }

        composable("profile_screen") {
            ProfileScreen(
                onNavigateToEditProfile = { navController.navigate("edit_profile_screen") },
                onNavigateToOrders = { navController.navigate("my_orders_screen") },
                onNavigateToNotifications = { navController.navigate("notifications_settings_screen") },
                onNavigateToSecurity = { navController.navigate("security_screen") },
                onNavigateToSettings = { navController.navigate("settings_screen") },
                onLogout = {
                    navController.navigate("welcome_screen") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable("edit_profile_screen") {
            EditProfileScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("notifications_settings_screen") {
            NotificationsSettingScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("security_screen") {
            SecurityScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("settings_screen") {
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("my_orders_screen") {
            MyOrdersScreen()
        }
    }
}