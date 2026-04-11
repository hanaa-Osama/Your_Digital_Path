package com.example.yourdigitalpath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.presentation.profile.screens.NotificationsScreen
import com.example.yourdigitalpath.presentation.viewModel.ProfileViewModel
import com.example.yourdigitalpath.ui.theme.YourDigitalPathTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.yourdigitalpath.presentation.AppNavGraph
import com.example.yourdigitalpath.data.dataSource.remote.FirestoreNotificationListener
import com.example.yourdigitalpath.ui.theme.YourDigitalPathTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Inject the FirestoreNotificationListener
    @Inject
    lateinit var firestoreNotificationListener: FirestoreNotificationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        firestoreNotificationListener.startListening()

        setContent {
            YourDigitalPathTheme  {
                AppNavGraph()
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
        NotificationsScreen(onBackClick = { /* Handle back navigation */ })
//        SettingsScreen(onBackClick = { /* Handle back navigation */ })
    }
}