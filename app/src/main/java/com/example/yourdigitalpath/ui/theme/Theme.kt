package com.example.yourdigitalpath.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

val LocalDarkTheme = compositionLocalOf { false }

@Composable
fun YourDigitalPathTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = DarkAppColors.Primary,
            onPrimary = DarkAppColors.Surface,
            background = DarkAppColors.Background,
            surface = DarkAppColors.Surface,
            onBackground = DarkAppColors.TextPrimary,
            onSurface = DarkAppColors.TextPrimary
        )
    } else {
        lightColorScheme(
            primary = LightAppColors.Primary,
            onPrimary = LightAppColors.Surface,
            background = LightAppColors.Background,
            surface = LightAppColors.Surface,
            onBackground = LightAppColors.TextPrimary,
            onSurface = LightAppColors.TextPrimary
        )
    }

    CompositionLocalProvider(LocalDarkTheme provides darkTheme) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}