package com.example.yourdigitalpath.Routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestViewModel
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestStep
import com.example.yourdigitalpath.presentation.service_request.DataScreen
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    val viewModel: ServiceRequestViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "service_step"
    ) {
        composable("service_step") {
            ServiceRequestStep(
                viewModel = viewModel,
                onNext = { navController.navigate("data_screen") }
            )
        }

        composable("data_screen") {
            DataScreen(
                viewModel = viewModel,
                onNextClick = { navController.navigate("summary_screen") }
            )
        }

        composable("summary_screen") {
            ServiceRequestScreen(
                viewModel = viewModel,
                onConfirm = {
                    navController.navigate("success_screen") {
                        popUpTo("service_step") { inclusive = true }
                    }
                }
            )
        }

        composable("success_screen") {
            // SuccessScreen()  هناهنضيف ضن سكرين يا بنات عشان ميعملش كراش الله يباركلكم لما المواطن الغلبان المسكين يدوس تأكيد
        }
    }
}