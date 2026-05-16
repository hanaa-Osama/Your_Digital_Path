package com.example.yourdigitalpath.presentation.profile.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.R
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

    val configuration = LocalConfiguration.current
    val isArabic = configuration.locales[0].language == "ar"
    val layoutDirection = if (isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(
        LocalLayoutDirection provides layoutDirection
    ) {
        Scaffold(
            topBar = {
                ProfileSimpleTopBar(
                    title = stringResource(R.string.notifications),
                    onBackClick = onBackClick
                )
            },
            containerColor = AppColors.Background
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(
                        horizontal = 20.dp,
                        vertical = 16.dp
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = stringResource(R.string.notification_preferences),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextPrimary
                )
                Text(
                    text = stringResource(R.string.choose_notifications),
                    fontSize = 12.sp,
                    color = AppColors.TextHint,
                    modifier = Modifier.padding(
                        top = 4.dp,
                        bottom = 20.dp
                    )
                )
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AppColors.Surface
                    ),
                    border = BorderStroke(
                        0.5.dp,
                        AppColors.Border
                    )
                ) {
                    Column {
                        NotificationSwitchItem(
                            title = stringResource(R.string.order_notifications),
                            subtitle = stringResource(R.string.order_notifications_subtitle),
                            icon = Icons.Outlined.NotificationsActive,
                            isChecked = settings?.orderNotifications ?: true,
                            onCheckedChange = {
                                viewModel.toggleOrderNotifications(it)
                            }
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            thickness = 0.5.dp,
                            color = AppColors.Border
                        )
                        NotificationSwitchItem(
                            title = stringResource(R.string.offers_updates),
                            subtitle = stringResource(R.string.offers_updates_subtitle),
                            icon = Icons.Outlined.Campaign,
                            isChecked = settings?.offersNotifications ?: false,
                            onCheckedChange = {
                                viewModel.toggleOffersNotifications(it)
                            }
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            thickness = 0.5.dp,
                            color = AppColors.Border
                        )
                        NotificationSwitchItem(
                            title = stringResource(R.string.system_notifications),
                            subtitle = stringResource(R.string.system_notifications_subtitle),
                            icon = Icons.Outlined.Notifications,
                            isChecked = settings?.systemNotifications ?: true,
                            onCheckedChange = {
                                viewModel.toggleSystemNotifications(it)
                            }
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

    NotificationsSettingScreen(
        onBackClick = {}
    )
}