package com.example.yourdigitalpath.presentation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blqes.digi.presentation.personalscreen.PersonalDataScreen
import com.blqes.digi.presentation.welcomscreen.LoginScreen
import com.example.yourdigitalpath.presentation.Home.MainScreen
import com.example.yourdigitalpath.presentation.dataEntry.DataScreen
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
            MainScreen("home_screen")
        }
        // Service Request Screen
        composable("service_request_screen") {
            ServiceRequestScreen(
                onNext = { navController.navigate("data_entry_screen") },
                onBack = { navController.popBackStack() }
            )
        }
        // Data Entry Screen
        composable("data_entry_screen") {
            DataScreen(
                onNext = { navController.navigate("file_upload_screen") },
                onBack = { navController.popBackStack() }
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



