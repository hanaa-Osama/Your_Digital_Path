package com.example.yourdigitalpath.Routes

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.blqes.digi.presentation.personalscreen.PersonalDataScreen
import com.blqes.digi.presentation.welcomscreen.LoginScreen
import com.example.yourdigitalpath.presentation.FileUploadScreen
import com.example.yourdigitalpath.presentation.Home.MainScreen
import com.example.yourdigitalpath.presentation.notification.NotificationViewModel
import com.example.yourdigitalpath.presentation.notification.NotificationsScreen
import com.example.yourdigitalpath.presentation.welcomscreen.WelcomeScreen
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestViewModel
import com.example.yourdigitalpath.presentation.service_request.ServiceSummaryScreen
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestScreen
import com.example.yourdigitalpath.presentation.service_request.ServiceDataEntryScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    val viewModel: ServiceRequestViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "welcome_screen"
    ) {
        composable("welcome_screen") { WelcomeScreen(navController) }
        composable("login_screen") { LoginScreen(navController) }
        composable("register_screen") { PersonalDataScreen("register_screen") }
        composable("home_screen") { MainScreen(navController) }

        composable("summary_screen") {
            ServiceSummaryScreen(
                viewModel = viewModel,
                onConfirm = { // تم التعديل هنا من onNext لـ onConfirm بناءً على الخطأ في الصورة
                    navController.navigate("data_screen")
                }
            )
        }

        composable("data_screen") {
            ServiceDataEntryScreen(
                viewModel = viewModel,
                onNextClick = {
                    navController.navigate("service_request_screen/default")
                }
            )
        }

        composable(
            "service_request_screen/{serviceName}",
            arguments = listOf(navArgument("serviceName") { type = NavType.StringType })
        ) { backStackEntry ->
            val serviceName = backStackEntry.arguments?.getString("serviceName") ?: ""
            ServiceRequestScreen(
                serviceName = serviceName,
                onNext = { navController.navigate("home_screen") },
                onBack = { navController.popBackStack() }
            )
        }

        composable("notifications_screen") {
            val notificationViewModel: NotificationViewModel = hiltViewModel()
            NotificationsScreen(
                onBack = { navController.popBackStack() },
                notificationViewModel = notificationViewModel
            )
        }

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