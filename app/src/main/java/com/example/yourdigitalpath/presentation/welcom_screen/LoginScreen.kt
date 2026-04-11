package com.example.yourdigitalpath.presentation.welcom_screen

import LoginHeader
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
            .padding(16.dp)
            ,
        verticalArrangement = Arrangement.Center,
        //horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
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

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Column {
                OutlinedTextField(
                    value = nationalId,
                    onValueChange = { nationalId = it },
                    label = { Text("الرقم القومي") },
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
