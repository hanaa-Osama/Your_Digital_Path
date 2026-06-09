package com.example.yourdigitalpath.presentation.profile.screens

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.MainActivity
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.presentation.profile.component.NotificationSwitchItem
import com.example.yourdigitalpath.presentation.profile.component.ProfileSimpleTopBar
import com.example.yourdigitalpath.presentation.viewModel.ProfileViewModel
import com.example.yourdigitalpath.ui.theme.AppColors

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val appSettings by viewModel.appSettings.collectAsState()
    val isDarkMode = appSettings?.displayMode == "dark"
    val isArabic = appSettings?.language == "ar"
    val activity = LocalContext.current as Activity

    Scaffold(
        topBar = {
            ProfileSimpleTopBar(
                title = stringResource(R.string.settings),
                onBackClick = onBackClick
            )
        },
        containerColor = AppColors.Background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.system_preferences),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.TextPrimary
            )
            Text(
                text = stringResource(R.string.customize_app),
                fontSize = 12.sp,
                color = AppColors.TextHint,
                modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
            )

            Card(
                modifier = Modifier.animateContentSize(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
                border = BorderStroke(0.5.dp, AppColors.Border)
            ) {
                Column {
                    NotificationSwitchItem(
                        title = stringResource(R.string.dark_mode),
                        subtitle = if (isDarkMode)
                            stringResource(R.string.enabled_now)
                        else
                            stringResource(R.string.disabled_now),
                        icon = if (isDarkMode)
                            Icons.Outlined.DarkMode
                        else
                            Icons.Outlined.LightMode,
                        isChecked = isDarkMode,
                        onCheckedChange = {
                            viewModel.updateDisplayMode(if (it) "dark" else "light")
                        }
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        thickness = 0.5.dp,
                        color = AppColors.Border
                    )
                    NotificationSwitchItem(
                        title = stringResource(R.string.app_language),
                        subtitle = if (isArabic)
                            stringResource(R.string.arabic)
                        else
                            stringResource(R.string.english),
                        icon = Icons.Outlined.Language,
                        isChecked = !isArabic,
                        onCheckedChange = {
                            val language = if (it) "en" else "ar"
                            viewModel.updateLanguage(language)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                                activity.overrideActivityTransition(
                                    Activity.OVERRIDE_TRANSITION_OPEN,
                                    android.R.anim.fade_in,
                                    android.R.anim.fade_out
                                )
                            }
                            val intent = Intent(activity, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            activity.startActivity(intent)
                            activity.finish()
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, locale = "ar")
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen(onBackClick = {})
}
