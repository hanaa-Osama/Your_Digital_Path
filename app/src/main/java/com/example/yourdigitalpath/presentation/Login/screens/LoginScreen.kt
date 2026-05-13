package com.example.yourdigitalpath.presentation.Login.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.blqes.digi.Login.LoginButtons
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.presentation.Login.AuthViewModel
import com.example.yourdigitalpath.presentation.Login.LoginState
import com.example.yourdigitalpath.presentation.Login.component.CustomTextField
import com.example.yourdigitalpath.presentation.Login.component.SavedAccount

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    LaunchedEffect(Unit) {
        authViewModel.resetState()
    }

    LoginContent(
        authViewModel = authViewModel,
        navController = navController,
        onLoginSuccess = {
            navController.navigate("home_screen") {
                popUpTo("login_screen") { inclusive = true }
            }
        }
    )
}

@Composable
fun LoginContent(
    authViewModel: AuthViewModel,
    navController: NavController,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val state by authViewModel.loginState.collectAsState()
    val savedAccounts by authViewModel.savedAccounts.collectAsState()

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

        if (savedAccounts.isNotEmpty()) {
            Text(
                text = stringResource(
                    R.string.login_with_existing_account
                ),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3D5A80),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F7FA)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier.heightIn(max = 200.dp)
                ) {
                    items(savedAccounts) { account ->
                        SavedAccountItem(
                            account = account,
                            onSelect = {
                                email = account.email
                            },
                            onRemove = {
                                authViewModel.removeAccount(account.email)
                            }
                        )
                        if (savedAccounts.last() != account) {
                            HorizontalDivider(
                                color = Color(0xFFEAECF0),
                                thickness = 0.5.dp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
        Column {
            CustomTextField(
                value = email,
                onValueChange = { email = it },
                hint = stringResource(
                    R.string.email
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = password,
                onValueChange = { password = it },
                hint = stringResource(R.string.password),
                isPassword = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LoginButtons(
            onLoginClick = {
                authViewModel.login(
                    email = email,
                    password = password
                )
            },
            onRegisterClick = {
                navController.navigate("register_screen")
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

@Composable
fun SavedAccountItem(
    account: SavedAccount,
    onSelect: () -> Unit,
    onRemove: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        IconButton(
            onClick = onRemove,
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                Icons.Outlined.Close,
                contentDescription = null,
                tint = Color(0xFF9BA3B2),
                modifier = Modifier.size(16.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.weight(1f)
        ) {

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = account.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A2E)
                )
                Text(
                    text = account.email,
                    fontSize = 12.sp,
                    color = Color(0xFF9BA3B2)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF3D5A80))
            ) {
                Text(
                    text = account.initials,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}