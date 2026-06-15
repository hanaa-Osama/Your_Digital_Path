package com.example.yourdigitalpath.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.ui.theme.AppColors
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.navigate("welcome_screen") {
            popUpTo("splash_screen") { inclusive = true }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(AppColors.Background)) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.Primary)
        ) {
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .offset(x = 290.dp, y = (-40).dp)
                    .background(Color.White.copy(alpha = 0.15f), CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .align(Alignment.BottomStart)
                    .offset(x = (-40).dp, y = 30.dp)
                    .background(Color.White.copy(alpha = 0.15f), CircleShape)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon1),
                    contentDescription = stringResource(R.string.app_icon),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .border(2.dp, Color(0x63B7B6B6), RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp))
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.app_name),
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.welcome_subtitle),
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}