package com.blqes.digi.presentation.welcomscreen

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

import com.blqes.digi.viewmodel.AuthViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun LoginScreen(navController: NavController) {

    val authViewModel = AuthViewModel()

    LoginContent(
        authViewModel = authViewModel,
        onLoginSuccess = {
            navController.navigate("home_screen")
        }
    )
}

@Composable
fun LoginContent(authViewModel: AuthViewModel, onLoginSuccess: () -> Unit) {
    TODO("Not yet implemented")
}