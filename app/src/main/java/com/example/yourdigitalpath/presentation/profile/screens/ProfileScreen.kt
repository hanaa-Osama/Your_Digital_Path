package com.example.yourdigitalpath.presentation.profile.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.presentation.profile.component.MenuItemRow
import com.example.yourdigitalpath.presentation.viewModel.ProfileViewModel
import com.example.yourdigitalpath.ui.theme.AppColors

@Composable
fun ProfileScreen(
    onNavigateToEditProfile: () -> Unit,
    onNavigateToOrders: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToSecurity: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onLogout: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profile by viewModel.userProfile.collectAsState()
    val appSettings by viewModel.appSettings.collectAsState()
    val isDarkMode = appSettings?.displayMode == "dark"

    val initials = profile?.name
        ?.split(" ")
        ?.take(2)
        ?.mapNotNull { it.firstOrNull()?.toString() }
        ?.joinToString("")
        ?: "؟"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .verticalScroll(rememberScrollState())
    ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = if (isDarkMode) {
                                listOf(Color(0xFF1D2A44), Color(0xFF0F1929))
                            } else {
                                listOf(AppColors.Primary, Color(0xFF293241))
                            }
                        )
                    )
                    .padding(
                        horizontal = 24.dp,
                        vertical = 28.dp
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(
                                Color.White.copy(alpha = 0.2f)
                            )
                    ) {
                        Text(
                            text = initials,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = profile?.name
                            ?: stringResource(R.string.loading),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.national_id_label),
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Text(
                            text = (profile?.nationalId?.take(8)
                                ?: "") + "XXXXX",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            MenuGroup {
                MenuItemRow(
                    title = stringResource(R.string.my_profile),
                    subtitle = stringResource(R.string.edit_profile_subtitle),
                    icon = Icons.Outlined.Person,
                    onClick = onNavigateToEditProfile
                )
                HorizontalDivider(
                    color = AppColors.Border,
                    thickness = 0.5.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                MenuItemRow(
                    title = stringResource(R.string.my_orders),
                    subtitle = stringResource(R.string.orders_subtitle),
                    icon = Icons.Outlined.Description,
                    onClick = onNavigateToOrders
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            MenuGroup {
                MenuItemRow(
                    title = stringResource(R.string.notifications),
                    subtitle = stringResource(R.string.notifications_subtitle),
                    icon = Icons.Outlined.Notifications,
                    onClick = onNavigateToNotifications
                )
                HorizontalDivider(
                    color = AppColors.Border,
                    thickness = 0.5.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                MenuItemRow(
                    title = stringResource(R.string.security_privacy),
                    subtitle = stringResource(R.string.password_label),
                    icon = Icons.Outlined.Lock,
                    onClick = onNavigateToSecurity
                )
                HorizontalDivider(
                    color = AppColors.Border,
                    thickness = 0.5.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                MenuItemRow(
                    title = stringResource(R.string.settings),
                    subtitle = stringResource(R.string.language_units),
                    icon = Icons.Outlined.Settings,
                    onClick = onNavigateToSettings
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            MenuGroup {
                MenuItemRow(
                    title = stringResource(R.string.logout),
                    subtitle = "",
                    icon = Icons.Outlined.Logout,
                    onClick = {
                        viewModel.logout()
                        onLogout()
                    },
                    iconTint = AppColors.Danger,
                    iconBg = AppColors.DangerBg,
                    titleColor = AppColors.Danger,
                    showArrow = false
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
}

@Composable
private fun MenuGroup(
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        border = BorderStroke(
            1.dp,
            AppColors.Border
        )
    ) {
        Column(
            content = content
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    ProfileScreen(
        onNavigateToEditProfile = {},
        onNavigateToOrders = {},
        onNavigateToNotifications = {},
        onNavigateToSecurity = {},
        onNavigateToSettings = {},
        onLogout = {}
    )
}