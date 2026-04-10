package com.blqes.digi.presentation.welcomscreen

import LoginHeader
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.blqes.digi.Login.LoginButtons
import com.blqes.digi.viewmodel.AuthViewModel
import com.blqes.digi.viewmodel.LoginState
import com.example.yourdigitalpath.R

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
    var nationalId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val state = authViewModel.loginState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.icon1),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        LoginHeader()

        Spacer(modifier = Modifier.height(24.dp))

        Column {
            OutlinedTextField(
                value = nationalId,
                onValueChange = { nationalId = it },
                label = { Text("رقم قومي") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("كلمة السر") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LoginButtons(
            onLoginClick = {
                authViewModel.login(nationalId, password)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is LoginState.Loading -> CircularProgressIndicator()
            is LoginState.Error -> Text(state.message, color = Color.Red)
            is LoginState.Success -> LaunchedEffect(Unit) { onLoginSuccess() }
            else -> {}
        }
    }
}
