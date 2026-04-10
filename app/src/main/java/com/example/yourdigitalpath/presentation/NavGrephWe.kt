package com.example.yourdigitalpath.presentation



import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yourdigitalpath.presentation.Home.MainScreen
import com.blqes.digi.presentation.personalscreen.PersonalDataScreen
import com.blqes.digi.presentation.welcomscreen.LoginScreen
import com.example.yourdigitalpath.presentation.welcomscreen.WelcomeScreen


@Composable
fun AppNavGraph() {


    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = "welcome_screen"
    ) {


        composable("welcome_screen") {
            WelcomeScreen(navController)
        }

        // 👇 شاشة تسجيل الدخول (placeholder)
        composable("login_screen") {
            LoginScreen(navController)
        }

        // 👇 شاشة إنشاء حساب (placeholder)
        composable("register_screen") {
            PersonalDataScreen("register_screen")
        }
        composable("home_screen") {
            MainScreen("home_screen")
        }


//////////
            // أضف المسارات هنا لتطابق الموجود في الـ Event
            composable("id_details_screen") { /* الصفحة الخاصة بالبطاقة */ }
            composable("birth_details_screen") { /* الصفحة الخاصة بشهادة الميلاد */ }
            // ...
        }
        }



