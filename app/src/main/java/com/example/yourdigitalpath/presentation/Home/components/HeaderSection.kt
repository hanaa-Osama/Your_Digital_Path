package com.example.yourdigitalpath.presentation.Home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.presentation.viewModel.ProfileViewModel
import com.example.yourdigitalpath.ui.theme.AppColors
import com.example.yourdigitalpath.R

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier,
    userName: String,
    servicesCount: Int = 5
) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val appSettings by profileViewModel.appSettings.collectAsState()
    val isDarkMode = appSettings?.displayMode == "dark"

    val displayName =
        if (userName.isBlank())
            stringResource(R.string.default_user)
        else userName

    val headerGradientColors = if (isDarkMode) {
        listOf(
            Color(0xFF1D2A44),
            Color(0xFF0F1929)
        )
    } else {
        listOf(
            AppColors.Primary,
            Color(0xFF293241)
        )
    }
    val textColor = if (isDarkMode) Color(0xFFF5F7FA) else Color.White
    val textSecondaryColor = if (isDarkMode) Color(0xFFB8C1CC) else Color.White.copy(alpha = 0.7f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = headerGradientColors
                )
            )
            .padding(
                top = 50.dp,
                bottom = 35.dp,
                start = 24.dp,
                end = 24.dp
            )
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.welcome_message),
                        color = textSecondaryColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = displayName,
                        color = textColor,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = stringResource(R.string.header_description),
                color = textSecondaryColor,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Row(
                modifier = Modifier
                    .background(
                        color = if (isDarkMode) Color.White.copy(0.08f) else Color.White.copy(0.15f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(
                        horizontal = 16.dp,
                        vertical = 10.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            if (isDarkMode) Color(0xFF81C784) else Color(0xFF4CAF50),
                            shape = CircleShape
                        )
                        .border(
                            2.dp,
                            Color.White.copy(0.3f),
                            CircleShape
                        )
                )
                Spacer(
                    modifier = Modifier.width(10.dp)
                )
                Text(
                    text = stringResource(
                        R.string.available_services,
                        servicesCount
                    ),
                    color = textColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
private fun HeaderSectionPrev() {
    HeaderSection(
        userName = "هناء أسامة"
    )
}