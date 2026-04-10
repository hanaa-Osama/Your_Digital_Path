package com.example.yourdigitalpath.Routes

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.blqes.digi.presentation.personalscreen.PersonalDataScreen
import com.blqes.digi.presentation.welcomscreen.LoginScreen
import com.example.yourdigitalpath.presentation.FileUploadScreen
import com.example.yourdigitalpath.presentation.Home.MainScreen
import com.example.yourdigitalpath.presentation.dataEntry.DataScreen
import com.example.yourdigitalpath.presentation.notification.NotificationViewModel
import com.example.yourdigitalpath.presentation.notification.NotificationsScreen
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestScreen
import com.example.yourdigitalpath.presentation.welcomscreen.WelcomeScreen

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome_screen"
    ) {

        // Welcome Screen
        composable("welcome_screen") {
            WelcomeScreen(navController)
        }
        // Login Screen
        composable("login_screen") {
            LoginScreen(navController)
        }
        // Register Screen
        composable("register_screen") {
            PersonalDataScreen("register_screen")
        }
        // Home Screen
        composable("home_screen") {
            MainScreen(navController)
        }
        // Service Request Screen
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
        // Data Entry Screen
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
                notificationViewModel = viewModel
            )
        }
        
        // File Upload Screen
        composable("file_upload_screen") {
            FileUploadScreen(
                onNext = {
                    navController.navigate("home_screen") {
                        popUpTo("home_screen") { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
