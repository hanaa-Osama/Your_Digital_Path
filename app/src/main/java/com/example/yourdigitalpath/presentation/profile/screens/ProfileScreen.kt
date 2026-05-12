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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
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
    onNavigateToNotifications: () -> Unit,
    onNavigateToSecurity: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onLogout: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profile by viewModel.userProfile.collectAsState()

    val initials = profile?.name
        ?.split(" ")
        ?.take(2)
        ?.mapNotNull { it.firstOrNull()?.toString() }
        ?.joinToString("") ?: "؟"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(AppColors.Primary, Color(0xFF293241))
                    )
                )
                .padding(horizontal = 24.dp, vertical = 28.dp),
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
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
                            .background(Color.White.copy(alpha = 0.2f))
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
                        text = profile?.name ?: "جاري التحميل...",
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
                            text = "الرقم القومي: ",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Text(
                            text = (profile?.nationalId?.take(8) ?: "") + "XXXXX",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

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
                subtitle = "عرض حالة طلباتك الحالية",
                icon = Icons.Outlined.Description,
                onClick = onNavigateToOrders
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        MenuGroup {
            MenuItemRow(
                title = "الإشعارات",
                subtitle = "تفعيل / إيقاف التنبيهات",
                icon = Icons.Outlined.Notifications,
                onClick = onNavigateToNotifications
            )
            HorizontalDivider(
                color = AppColors.Border,
                thickness = 0.5.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            MenuItemRow(
                title = "الأمان والخصوصية",
                subtitle = "كلمة المرور — البصمة",
                icon = Icons.Outlined.Lock,
                onClick = onNavigateToSecurity
            )
            HorizontalDivider(
                color = AppColors.Border,
                thickness = 0.5.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            MenuItemRow(
                title = "الإعدادات",
                subtitle = "اللغة — الوحدات",
                icon = Icons.Outlined.Settings,
                onClick = onNavigateToSettings
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

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

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun MenuGroup(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, Color(0xFFEAECF0))
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
        onNavigateToNotifications = {},
        onNavigateToSecurity = {},
        onNavigateToSettings = {},
        onLogout = {}
    )
}
