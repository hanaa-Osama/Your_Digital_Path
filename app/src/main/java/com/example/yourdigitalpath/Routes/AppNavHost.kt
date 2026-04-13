package com.example.yourdigitalpath.Routes

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.blqes.digi.viewmodel.AuthViewModel
import com.blqes.digi.viewmodel.LoginState
import com.example.yourdigitalpath.presentation.Home.MainScreen
import com.example.yourdigitalpath.presentation.data_entry.DataScreen
import com.example.yourdigitalpath.presentation.data_entry.certificates.BirthCertificateViewModel
import com.example.yourdigitalpath.presentation.notification.NotificationViewModel
import com.example.yourdigitalpath.presentation.notification.screen.NotificationsScreen
import com.example.yourdigitalpath.presentation.order_track.TrackingDetailsScreen
import com.example.yourdigitalpath.presentation.order_track.TrackingViewModel
import com.example.yourdigitalpath.presentation.orders_history.screens.MyOrdersScreen
import com.example.yourdigitalpath.presentation.personal_screen.AccountDataScreen
import com.example.yourdigitalpath.presentation.profile.screens.EditProfileScreen
import com.example.yourdigitalpath.presentation.profile.screens.NotificationsSettingScreen
import com.example.yourdigitalpath.presentation.profile.screens.ProfileScreen
import com.example.yourdigitalpath.presentation.profile.screens.SecurityScreen
import com.example.yourdigitalpath.presentation.profile.screens.SettingsScreen
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestScreen
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestViewModel
import com.example.yourdigitalpath.presentation.uploadfile.ServiceSummaryScreen
import com.example.yourdigitalpath.presentation.uploadfile.UploudFilesScreens
import com.example.yourdigitalpath.presentation.welcom_screen.LoginScreen
import com.example.yourdigitalpath.presentation.welcom_screen.WelcomeScreen


@Composable
fun AppNavHost(navController: NavHostController) {

    val authViewModel: AuthViewModel = hiltViewModel()

    val userName by remember {
        derivedStateOf {
            val state = authViewModel.loginState
            if (state is LoginState.Success) state.userName else ""
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        "home_screen",
        "notifications_screen",
        "profile_screen",
        "my_orders_screen"
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
            composable("welcome_screen") { WelcomeScreen(navController) }

            composable("login_screen") { LoginScreen(navController) }

            composable("register_screen") {
                PersonalDataScreen(
                    onBack = { navController.popBackStack() },
                    onNext = { navController.navigate("account_data_screen") }
                )
            }

            composable("account_data_screen") {
                AccountDataScreen(
                    onBack = { navController.popBackStack() },
                    onRegister = {
                        navController.navigate("home_screen") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            composable("home_screen") {
                MainScreen(
                    navController = navController,
                    onBack = { navController.navigate("home_screen") },
                    userName = userName
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

            composable(
                "data_entry_screen/{serviceName}",
                arguments = listOf(navArgument("serviceName") { type = NavType.StringType })
            ) { backStackEntry ->
                val serviceName = backStackEntry.arguments?.getString("serviceName") ?: ""
                DataScreen(
                    serviceName = serviceName,
                    onNext = { navController.navigate("file_upload_screen/$serviceName") },
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                "file_upload_screen/{serviceName}",
                arguments = listOf(navArgument("serviceName") { type = NavType.StringType })
            ) { backStackEntry ->
                val serviceName = backStackEntry.arguments?.getString("serviceName") ?: ""
                val viewModel: ServiceRequestViewModel = hiltViewModel()
                UploudFilesScreens(
                    serviceName = serviceName,
                    viewModel = viewModel,
                    onNextClick = { navController.navigate("summary_screen/$serviceName") },
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                "summary_screen/{serviceName}",
                arguments = listOf(navArgument("serviceName") { type = NavType.StringType })
            ) { backStackEntry ->
                val serviceName = backStackEntry.arguments?.getString("serviceName") ?: ""
                val viewModelService: ServiceRequestViewModel = hiltViewModel()
                val viewModelBirth: BirthCertificateViewModel = hiltViewModel()
                val trackingViewModel: TrackingViewModel = hiltViewModel()
                ServiceSummaryScreen(
                    serviceName = serviceName,
                    serviceRequestViewModel = viewModelService,
                    birthCertificateViewModel = viewModelBirth,
                    onConfirm = {
                        trackingViewModel.confirmAndSubmitOrder { orderId ->
                            navController.navigate("tracking_details/$orderId") {
                                popUpTo("home_screen") { inclusive = false }
                            }
                        }
                    }
                )
            }

            composable("notifications_screen") {
                val viewModel: NotificationViewModel = hiltViewModel()
                NotificationsScreen(
                    onBack = { navController.popBackStack() },
                    notificationViewModel = viewModel,
                )
            }

            composable(
                route = "tracking_details/{orderId}",
                arguments = listOf(navArgument("orderId") { type = NavType.StringType })
            ) { backStackEntry ->
                val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
                TrackingDetailsScreen(
                    orderId = orderId,
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
                EditProfileScreen(onBackClick = { navController.popBackStack() })
            }

            composable("notifications_settings_screen") {
                NotificationsSettingScreen(onBackClick = { navController.popBackStack() })
            }

            composable("security_screen") {
                SecurityScreen(onBackClick = { navController.popBackStack() })
            }

            composable("settings_screen") {
                SettingsScreen(onBackClick = { navController.popBackStack() })
            }

            composable("my_orders_screen") {
                MyOrdersScreen()
            }
        }
    }
}
