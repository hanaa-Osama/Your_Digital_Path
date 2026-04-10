package com.blqes.digi.Login

import LoginHeader
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blqes.digi.viewmodel.AuthViewModel
import com.blqes.digi.viewmodel.LoginState
import com.example.yourdigitalpath.R

@Composable
fun LoginContent(
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit
) {
    var nationalId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val state = authViewModel.loginState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Image(painter = painterResource(id = R.drawable.icon1),
            contentDescription = null)
        Spacer(modifier = Modifier.height(16.dp))
        LoginHeader()

        Spacer(modifier = Modifier.height(24.dp))

        // LoginForm
        Column {
            androidx.compose.material3.TextField(
                value = nationalId,
                onValueChange = { nationalId = it },
                label = { Text("رقم قومي") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            androidx.compose.material3.TextField(
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
            is LoginState.Success -> onLoginSuccess()
            else -> {}
        }
    }
}

@Composable
@Preview
private fun Login() {
    LoginContent(
        authViewModel = AuthViewModel(

        ),
        onLoginSuccess = {}
    )

}