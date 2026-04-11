package com.example.yourdigitalpath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.yourdigitalpath.Routes.AppNavHost
import com.example.yourdigitalpath.Routes.rememberAppNavController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.data.dataSource.remote.FirestoreNotificationListener
import com.example.yourdigitalpath.presentation.profile.screens.NotificationsSettingScreen
import com.example.yourdigitalpath.presentation.viewModel.ProfileViewModel
import com.example.yourdigitalpath.ui.theme.YourDigitalPathTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firestoreNotificationListener: FirestoreNotificationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        firestoreNotificationListener.startListening()

        setContent {
            YourDigitalPathTheme  {
                val navController = rememberAppNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}

@Composable
fun AppContent() {
    val viewModel: ProfileViewModel = hiltViewModel()
    val appSettings by viewModel.appSettings.collectAsState()
    val isDarkTheme = appSettings?.displayMode == "الوضع المظلم"

    YourDigitalPathTheme(darkTheme = isDarkTheme) {
        NotificationsSettingScreen(onBackClick = { /* Handle back navigation */ })
//        SettingsScreen(onBackClick = { /* Handle back navigation */ })
    }
}