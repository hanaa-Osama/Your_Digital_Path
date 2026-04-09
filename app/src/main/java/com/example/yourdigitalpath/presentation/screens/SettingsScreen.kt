package com.example.yourdigitalpath.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.presentation.components.profile_component.ActionButton
import com.example.yourdigitalpath.presentation.components.profile_component.ProfileSimpleTopBar
import com.example.yourdigitalpath.presentation.components.profile_component.SettingItem
import com.example.yourdigitalpath.presentation.viewModel.ProfileViewModel
import com.example.yourdigitalpath.ui.theme.AppColors

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val appSettings by viewModel.appSettings.collectAsState()

    var showLanguageDialog    by remember { mutableStateOf(false) }
    var showDisplayDialog     by remember { mutableStateOf(false) }

    val languages    = listOf("العربية", "English")
    val displayModes = listOf("الوضع الفاتح", "الوضع المظلم")

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                ProfileSimpleTopBar(
                    title = "الإعدادات",
                    onBackClick = onBackClick
                )
            },
            containerColor = AppColors.Background
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = "تفضيلات النظام",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextPrimary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AppColors.Surface
                    ),
                    border = BorderStroke(0.5.dp, AppColors.Border)
                ) {
                    Column {
                        SettingItem(
                            title = "لغة التطبيق",
                            value = appSettings?.language ?: "العربية",
                            icon = Icons.Outlined.Language,
                            onClick = { showLanguageDialog = true }
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            thickness = 0.5.dp,
                            color = AppColors.Border
                        )
                        SettingItem(
                            title = "وضع العرض",
                            value = appSettings?.displayMode ?: "الوضع الفاتح",
                            icon = Icons.Outlined.LightMode,
                            onClick = { showDisplayDialog = true }
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                ActionButton(
                    text = "حفظ التغييرات",
                    onClick = onBackClick
                )
            }
        }

        if (showLanguageDialog) {
            AlertDialog(
                onDismissRequest = { showLanguageDialog = false },
                title = {
                    Text(
                        text = "اختر اللغة",
                        fontWeight = FontWeight.Bold,
                        color = AppColors.TextPrimary
                    )
                },
                text = {
                    Column {
                        languages.forEach { lang ->
                            Text(
                                text = lang,
                                fontSize = 14.sp,
                                color = if (appSettings?.language == lang)
                                    AppColors.Primary else AppColors.TextPrimary,
                                fontWeight = if (appSettings?.language == lang)
                                    FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.updateLanguage(lang)
                                        showLanguageDialog = false
                                    }
                                    .padding(vertical = 10.dp)
                            )
                        }
                    }
                },
                confirmButton = {}
            )
        }

        if (showDisplayDialog) {
            AlertDialog(
                onDismissRequest = { showDisplayDialog = false },
                title = {
                    Text(
                        text = "وضع العرض",
                        fontWeight = FontWeight.Bold,
                        color = AppColors.TextPrimary
                    )
                },
                text = {
                    Column {
                        displayModes.forEach { mode ->
                            Text(
                                text = mode,
                                fontSize = 14.sp,
                                color = if (appSettings?.displayMode == mode)
                                    AppColors.Primary else AppColors.TextPrimary,
                                fontWeight = if (appSettings?.displayMode == mode)
                                    FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.updateDisplayMode(mode)
                                        showDisplayDialog = false
                                    }
                                    .padding(vertical = 10.dp)
                            )
                        }
                    }
                },
                confirmButton = {}
            )
        }
    }
}

@Preview(showBackground = true, locale = "ar")
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen(onBackClick = {})
}