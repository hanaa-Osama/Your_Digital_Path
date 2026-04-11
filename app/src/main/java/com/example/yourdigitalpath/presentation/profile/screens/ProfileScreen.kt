package com.example.yourdigitalpath.presentation.profile.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.presentation.profile.component.MenuItemRow
import com.example.yourdigitalpath.presentation.viewModel.ProfileViewModel
import com.example.yourdigitalpath.ui.theme.AppColors

@Composable
fun ProfileScreen(
    onNavigateToEditProfile: () -> Unit,
    onNavigateToOrders: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onLogout: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profile by viewModel.userProfile.collectAsState()

    val initials = remember(profile?.name) {
        profile?.name
            ?.split(" ")
            ?.take(2)
            ?.mapNotNull { it.firstOrNull()?.toString() }
            ?.joinToString("") ?: "؟"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .verticalScroll(rememberScrollState())
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.Primary)
                .padding(horizontal = 20.dp, vertical = 28.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                ) {
                    Text(
                        text = initials,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = profile?.name ?: "...",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 3.dp)
                ) {
                    Text(
                        text = profile?.nationalId?.take(8) ?: "",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                    Text(
                        text = "XXXXX",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                    Text(
                        text = " :الرقم القومي",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))


        MenuGroup {
            MenuItemRow(
                title = "بياناتي الشخصية",
                subtitle = "تعديل الاسم والتواصل",
                icon = Icons.Outlined.Person,
                onClick = onNavigateToEditProfile
            )
            HorizontalDivider(
                color = AppColors.Border,
                thickness = 0.5.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            MenuItemRow(
                title = "سجل طلباتي",
                subtitle = "${0} طلبات مكتملة",
                icon = Icons.Outlined.Description,
                onClick = onNavigateToOrders
            )
        }

        Spacer(modifier = Modifier.height(10.dp))


        MenuGroup {
            MenuItemRow(
                title = "الإشعارات",
                subtitle = "تفعيل / إيقاف التنبيهات",
                icon = Icons.Outlined.Notifications,
                onClick = onNavigateToSettings
            )
            HorizontalDivider(color = AppColors.Border, thickness = 0.5.dp,
                modifier = Modifier.padding(horizontal = 16.dp))
            MenuItemRow(
                title = "الأمان والخصوصية",
                subtitle = "كلمة المرور — البصمة",
                icon = Icons.Outlined.Lock,
                onClick = onNavigateToSettings
            )
            HorizontalDivider(color = AppColors.Border, thickness = 0.5.dp,
                modifier = Modifier.padding(horizontal = 16.dp))
            MenuItemRow(
                title = "الإعدادات",
                subtitle = "اللغة — الوحدات",
                icon = Icons.Outlined.Settings,
                onClick = onNavigateToSettings
            )
        }

        Spacer(modifier = Modifier.height(10.dp))


        MenuGroup {
            MenuItemRow(
                title = "تسجيل الخروج",
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

    }
}

@Composable
private fun MenuGroup(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(0.5.dp, AppColors.Border)
    ) {
        Column(content = content)
    }
}

@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen(
        onNavigateToEditProfile = {},
        onNavigateToOrders = {},
        onNavigateToSettings = {},
        onLogout = {}
    )
}