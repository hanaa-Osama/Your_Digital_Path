package com.example.yourdigitalpath.Routes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.blqes.digi.presentation.BottomNavBar
import com.blqes.digi.presentation.personalscreen.PersonalDataScreen
import com.blqes.digi.presentation.welcomscreen.LoginScreen
import com.example.yourdigitalpath.presentation.FileUploadScreen
import com.example.yourdigitalpath.presentation.Home.MainScreen
import com.example.yourdigitalpath.presentation.dataEntry.DataScreen
import com.example.yourdigitalpath.presentation.notification.NotificationViewModel
import com.example.yourdigitalpath.presentation.notification.NotificationsScreen
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestScreen
import com.example.yourdigitalpath.presentation.welcom_screen.WelcomeScreen


@Composable
fun AppNavHost(
    navController: NavHostController
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        "home_screen",
        "notifications_screen"
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController)
            }
        }
    ) { padding ->

    NavHost(
        navController = navController,
        startDestination = "welcome_screen",
                modifier = Modifier.padding(padding)
    ) {

        composable("welcome_screen") {
            WelcomeScreen(navController)
        }

        composable("login_screen") {
            LoginScreen(navController)
        }

        composable("register_screen") {
            PersonalDataScreen("register_screen")
        }

        composable("home_screen") {
            MainScreen(navController = navController, onBack = {})
        }

        composable(
            "service_request_screen/{serviceName}",
            arguments = listOf(navArgument("serviceName") { type = NavType.StringType })
        ) { backStackEntry ->
            val serviceName = backStackEntry.arguments?.getString("serviceName") ?: ""
            ServiceRequestScreen(
                serviceName = serviceName,
                onNext = { navController.navigate("data_entry_screen/$serviceName") },
                onBack = { navController.popBackStack() }
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
}}