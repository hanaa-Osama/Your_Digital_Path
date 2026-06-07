package com.example.yourdigitalpath

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.Routes.AppNavHost
import com.example.yourdigitalpath.Routes.rememberAppNavController
import com.example.yourdigitalpath.data.dataSource.remote.FirestoreNotificationListener
import com.example.yourdigitalpath.presentation.viewModel.ProfileViewModel
import com.example.yourdigitalpath.ui.theme.YourDigitalPathTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firestoreNotificationListener: FirestoreNotificationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        // ★ طبّق اللغة قبل أي حاجة تانية عشان الـ Resources تتحمّل صح
        val sharedPrefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val language = sharedPrefs.getString("app_language", "ar") ?: "ar"
        val displayMode = sharedPrefs.getString("display_mode", "light") ?: "light"
        LocaleManager.setLocale(this, language)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        firestoreNotificationListener.startListening()

        setContent {
            val profileViewModel: ProfileViewModel = hiltViewModel()
            val appSettings by profileViewModel.appSettings.collectAsState()
            val isDarkMode = (appSettings?.displayMode ?: displayMode) == "dark"

            YourDigitalPathTheme(darkTheme = isDarkMode) {
                val navController = rememberAppNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}
