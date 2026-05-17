package com.example.yourdigitalpath.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.view.WindowCompat

val LocalDarkTheme = compositionLocalOf { false }

@Composable
fun YourDigitalPathTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val isDarkMode = darkTheme
    val colorScheme = if (isDarkMode) {
        darkColorScheme(
            primary = DarkAppColors.Primary,
            onPrimary = DarkAppColors.Surface,
            primaryContainer = DarkAppColors.PrimaryLight,
            onPrimaryContainer = DarkAppColors.Primary,
            background = DarkAppColors.Background,
            onBackground = DarkAppColors.TextPrimary,
            surface = DarkAppColors.Surface,
            onSurface = DarkAppColors.TextPrimary,
            surfaceVariant = DarkAppColors.PrimaryLight,
            onSurfaceVariant = DarkAppColors.TextSecond,
            outline = DarkAppColors.Border,
            error = DarkAppColors.Danger,
            onError = DarkAppColors.Surface
        )
    } else {
        lightColorScheme(
            primary = LightAppColors.Primary,
            onPrimary = LightAppColors.Surface,
            primaryContainer = LightAppColors.PrimaryLight,
            onPrimaryContainer = LightAppColors.Primary,
            background = LightAppColors.Background,
            onBackground = LightAppColors.TextPrimary,
            surface = LightAppColors.Surface,
            onSurface = LightAppColors.TextPrimary,
            surfaceVariant = LightAppColors.PrimaryLight,
            onSurfaceVariant = LightAppColors.TextSecond,
            outline = LightAppColors.Border,
            error = LightAppColors.Danger,
            onError = LightAppColors.Surface
        )
    }

    val view = LocalView.current
    val configuration = LocalConfiguration.current
    val isArabic = configuration.locales[0].language == "ar"
    val layoutDirection = if (isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDarkMode
        }
    }

    CompositionLocalProvider(
        LocalDarkTheme provides isDarkMode,
        LocalLayoutDirection provides layoutDirection
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}