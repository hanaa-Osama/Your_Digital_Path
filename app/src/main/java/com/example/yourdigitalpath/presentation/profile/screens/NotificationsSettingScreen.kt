package com.example.yourdigitalpath.presentation.profile.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.presentation.profile.component.NotificationSwitchItem
import com.example.yourdigitalpath.presentation.profile.component.ProfileSimpleTopBar
import com.example.yourdigitalpath.presentation.viewModel.ProfileViewModel
import com.example.yourdigitalpath.ui.theme.AppColors

@Composable
fun NotificationsSettingScreen(
    onBackClick: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val settings by viewModel.notificationSettings.collectAsState()

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                ProfileSimpleTopBar(
                    title = "الإشعارات",
                    onBackClick = onBackClick
                )
            },
            containerColor = AppColors.Background
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "تفضيلات التنبيهات",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextPrimary
                )
                Text(
                    text = "اختر التنبيهات التي ترغب في استقبالها",
                    fontSize = 12.sp,
                    color = AppColors.TextHint,
                    modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
                )

                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AppColors.Surface
                    ),
                    border = BorderStroke(0.5.dp, AppColors.Border)
                ) {
                    Column {
                        NotificationSwitchItem(
                            title = "تنبيهات الطلبات",
                            subtitle = "إشعار عند تغيير حالة الطلب",
                            icon = Icons.Outlined.NotificationsActive,
                            isChecked = settings?.orderNotifications ?: true,
                            onCheckedChange = { viewModel.toggleOrderNotifications(it) }
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            thickness = 0.5.dp,
                            color = AppColors.Border
                        )
                        NotificationSwitchItem(
                            title = "العروض والتحديثات",
                            subtitle = "رسائل عن الميزات الجديدة",
                            icon = Icons.Outlined.Campaign,
                            isChecked = settings?.offersNotifications ?: false,
                            onCheckedChange = { viewModel.toggleOffersNotifications(it) }
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            thickness = 0.5.dp,
                            color = AppColors.Border
                        )
                        NotificationSwitchItem(
                            title = "إشعارات النظام",
                            subtitle = "تنبيهات مهمة تخص حسابك",
                            icon = Icons.Outlined.Notifications,
                            isChecked = settings?.systemNotifications ?: true,
                            onCheckedChange = { viewModel.toggleSystemNotifications(it) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, locale = "ar")
@Composable
fun PreviewNotificationsSettingScreen() {
    NotificationsSettingScreen(onBackClick = {})
}