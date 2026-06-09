package com.example.yourdigitalpath.Routes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.blqes.digi.presentation.BottomNavBar
import com.example.yourdigitalpath.presentation.Home.screens.MainScreen
import com.example.yourdigitalpath.presentation.Login.AuthViewModel
import com.example.yourdigitalpath.presentation.Login.LoginState
import com.example.yourdigitalpath.presentation.Login.screens.LoginScreen
import com.example.yourdigitalpath.presentation.Register.RegisterViewModel
import com.example.yourdigitalpath.presentation.Register.screens.AccountDataScreen
import com.example.yourdigitalpath.presentation.Register.screens.PersonalDataScreen
import com.example.yourdigitalpath.presentation.data_entry.DataScreen
import com.example.yourdigitalpath.presentation.notification.NotificationViewModel
import com.example.yourdigitalpath.presentation.notification.screen.NotificationsScreen
import com.example.yourdigitalpath.presentation.order_track.TrackingDetailsScreen
import com.example.yourdigitalpath.presentation.orders_history.screens.MyOrdersScreen
import com.example.yourdigitalpath.presentation.profile.screens.EditProfileScreen
import com.example.yourdigitalpath.presentation.profile.screens.NotificationsSettingScreen
import com.example.yourdigitalpath.presentation.profile.screens.ProfileScreen
import com.example.yourdigitalpath.presentation.profile.screens.SecurityScreen
import com.example.yourdigitalpath.presentation.profile.screens.SettingsScreen
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestScreen
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestViewModel
import com.example.yourdigitalpath.presentation.uploadfile.ServiceSummaryScreen
import com.example.yourdigitalpath.presentation.uploadfile.UploudFilesScreens
import com.example.yourdigitalpath.presentation.welcom_screen.WelcomeScreen

@Composable
fun AppNavHost(navController: NavHostController) {

    val authViewModel: AuthViewModel = hiltViewModel()
    val loginState by authViewModel.loginState.collectAsState()


    val startDestination = remember {
        if (authViewModel.isUserAlreadyLoggedIn()) "home_screen" else "welcome_screen"
    }
    if (authViewModel.isUserAlreadyLoggedIn() && loginState is LoginState.Idle) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFF3D5A80))
        }
        return
    }
    val userName by authViewModel.userName.collectAsState()
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
            startDestination = startDestination,
            modifier = Modifier.padding(padding)
        ) {

            composable("welcome_screen") {
                WelcomeScreen(navController)
            }

            composable("login_screen") {
                LoginScreen(
                    navController = navController,
                    authViewModel = authViewModel
                )
            }

            navigation(
                startDestination = "register_screen",
                route = "register_root"
            ) {
                composable("register_screen") { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("register_root")
                    }
                    val registerViewModel: RegisterViewModel =
                        hiltViewModel(parentEntry)
                    PersonalDataScreen(
                        viewModel = registerViewModel,
                        onBack = { navController.popBackStack() },
                        onNext = {
                            navController.navigate("account_data_screen")
                        }
                    )
                }

                composable("account_data_screen") { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("register_root")
                    }
                    val registerViewModel: RegisterViewModel =
                        hiltViewModel(parentEntry)
                    AccountDataScreen(
                        viewModel = registerViewModel,
                        onBack = { navController.popBackStack() },
                        onRegisterSuccess = {
                            authViewModel.refreshAfterRegister()
                            navController.navigate("home_screen") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }
            }

            composable("home_screen") {
                MainScreen(
                    navController = navController,
                    onBack = { },
                    userName = userName
                )
            }

            navigation(
                startDestination = "service_request_screen/{serviceName}",
                route = "service_root"
            ) {
                composable(
                    "service_request_screen/{serviceName}",
                    arguments = listOf(navArgument("serviceName") { type = NavType.StringType })
                ) { backStackEntry ->
                    val serviceName = backStackEntry.arguments?.getString("serviceName") ?: ""
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("service_root")
                    }
                    val viewModel: ServiceRequestViewModel = hiltViewModel(parentEntry)
                    ServiceRequestScreen(
                        serviceName = serviceName,
                        navController = navController,
                        viewModel = viewModel,
                        onNext = { navController.navigate("data_entry_screen/$serviceName") },
                        onBack = { navController.popBackStack() }
                    )
                }

                composable(
                    "data_entry_screen/{serviceName}",
                    arguments = listOf(navArgument("serviceName") { type = NavType.StringType })
                ) { backStackEntry ->
                    val serviceName = backStackEntry.arguments?.getString("serviceName") ?: ""
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("service_root")
                    }
                    val viewModel: ServiceRequestViewModel = hiltViewModel(parentEntry)
                    DataScreen(
                        serviceName = serviceName,
                        viewModel = viewModel,
                        onNext = { navController.navigate("file_upload_screen/$serviceName") },
                        onBack = { navController.popBackStack() }
                    )
                }

                composable("file_upload_screen/{serviceName}") { backStackEntry ->
                    val serviceName = backStackEntry.arguments?.getString("serviceName") ?: ""
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("service_root")
                    }
                    val viewModel: ServiceRequestViewModel = hiltViewModel(parentEntry)
                    UploudFilesScreens(
                        serviceName = serviceName,
                        viewModel = viewModel,
                        onNextClick = { navController.navigate("summary_screen/$serviceName") },
                        onBack = { navController.popBackStack() }
                    )
                }

                composable("summary_screen/{serviceName}") { backStackEntry ->
                    val serviceName = backStackEntry.arguments?.getString("serviceName") ?: ""
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("service_root")
                    }
                    val viewModel: ServiceRequestViewModel = hiltViewModel(parentEntry)
                    ServiceSummaryScreen(
                        serviceName = serviceName,
                        serviceRequestViewModel = viewModel,
                        onConfirm = {
                            viewModel.saveServiceRequest { orderId ->
                                navController.navigate("tracking_details/$orderId") {
                                    popUpTo("home_screen") { inclusive = false }
                                }
                            }
                        }
                    )
                }
            }

            composable(
                route = "tracking_details/{orderId}",
                arguments = listOf(navArgument("orderId") { type = NavType.StringType })
            ) { backStackEntry ->
                val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
                TrackingDetailsScreen(
                    orderId = orderId,
                    navController = navController,
                    onBack = {
                        if (!navController.popBackStack("my_orders_screen", false)) {
                            navController.navigate("my_orders_screen") {
                                popUpTo("home_screen")
                            }
                        }
                    }
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
                        authViewModel.logout()
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
                MyOrdersScreen(
                    onOrderClick = { orderId ->
                        navController.navigate("tracking_details/$orderId")
                    }
                )
            }

            composable("notifications_screen") {
                val viewModel: NotificationViewModel = hiltViewModel()
                NotificationsScreen(notificationViewModel = viewModel)
            }
        }
    }
}