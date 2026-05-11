package com.example.yourdigitalpath.presentation.Login.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.blqes.digi.Login.LoginButtons
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.presentation.Login.AuthViewModel
import com.example.yourdigitalpath.presentation.Login.LoginState
import com.example.yourdigitalpath.presentation.Login.component.CustomTextField

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    LoginContent(
        authViewModel = authViewModel,
        onLoginSuccess = {
            navController.navigate("home_screen") {
                popUpTo("login_screen") {
                    inclusive = true
                }
            }
        }
    )
}

@Composable
fun LoginContent(
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // ← التعديل هنا
    val state by authViewModel.loginState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LoginHeader()
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(id = R.drawable.icon1),
                contentDescription = "icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .border(
                        width = 2.dp,
                        color = Color(0x63B7B6B6),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clip(RoundedCornerShape(20.dp))
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        CompositionLocalProvider(
            LocalLayoutDirection provides LayoutDirection.Rtl
        ) {
            Column {
                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    hint = "البريد الإلكتروني"
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    hint = "كلمة المرور",
                    isPassword = true
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LoginButtons(
            onLoginClick = {
                authViewModel.login(
                    email = email,
                    password = password
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        when (state) {
            is LoginState.Loading -> {
                CircularProgressIndicator()
            }
            is LoginState.Error -> {
                Text(
                    text = (state as LoginState.Error).message,
                    color = Color.Red
                )
            }
            is LoginState.Success -> {
                LaunchedEffect(Unit) {
                    onLoginSuccess()
                }
            }
            else -> {}
        }
    }
}