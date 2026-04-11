package com.example.yourdigitalpath.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

data class AppColorScheme(
    val Primary: Color,
    val PrimaryLight: Color,
    val PrimaryMid: Color,
    val Background: Color,
    val Surface: Color,
    val Border: Color,
    val TextPrimary: Color,
    val TextSecond: Color,
    val TextHint: Color,
    val Success: Color,
    val SuccessBg: Color,
    val Warning: Color,
    val WarningBg: Color,
    val Danger: Color,
    val DangerBg: Color
)

val LightAppColors = AppColorScheme(
    Primary = Color(0xFF3D5A80),
    PrimaryLight = Color(0xFFEBF0F7),
    PrimaryMid = Color(0xFF98C1D9),
    Background = Color(0xFFF7F8FA),
    Surface = Color(0xFFFFFFFF),
    Border = Color(0xFFE4E8ED),
    TextPrimary = Color(0xFF1A1D23),
    TextSecond = Color(0xFF5C6478),
    TextHint = Color(0xFF9BA3B2),
    Success = Color(0xFF3A7D5A),
    SuccessBg = Color(0xFFEAF4EE),
    Warning = Color(0xFF8A6A1F),
    WarningBg = Color(0xFFFDF5E0),
    Danger = Color(0xFFC0392B),
    DangerBg = Color(0xFFFEF0F0)
)

val DarkAppColors = AppColorScheme(
    Primary      = Color(0xFF7BA7C9),
    PrimaryLight = Color(0xFF1E2D3D),
    PrimaryMid   = Color(0xFF4A7A9B),
    Background   = Color(0xFF0F1418),
    Surface      = Color(0xFF1A2332),
    Border       = Color(0xFF2A3A4A),
    TextPrimary  = Color(0xFFE8EDF2),
    TextSecond   = Color(0xFF8FA8C0),
    TextHint     = Color(0xFF506070),
    Success      = Color(0xFF5DBF85),
    SuccessBg    = Color(0xFF0D2A1A),
    Warning      = Color(0xFFD4A843),
    WarningBg    = Color(0xFF2A1F08),
    Danger       = Color(0xFFE06B6B),
    DangerBg     = Color(0xFF2A0D0D)
)

@Composable
fun appColors(): AppColorScheme {
    return if (LocalDarkTheme.current) DarkAppColors else LightAppColors
}

object AppColors {
    val Primary: Color @Composable get() = appColors().Primary
    val PrimaryLight: Color @Composable get() = appColors().PrimaryLight
    val PrimaryMid: Color @Composable get() = appColors().PrimaryMid
    val Background: Color @Composable get() = appColors().Background
    val Surface: Color @Composable get() = appColors().Surface
    val Border: Color @Composable get() = appColors().Border
    val TextPrimary: Color @Composable get() = appColors().TextPrimary
    val TextSecond: Color @Composable get() = appColors().TextSecond
    val TextHint: Color @Composable get() = appColors().TextHint
    val Success: Color @Composable get() = appColors().Success
    val SuccessBg: Color @Composable get() = appColors().SuccessBg
    val Warning: Color @Composable get() = appColors().Warning
    val WarningBg: Color @Composable get() = appColors().WarningBg
    val Danger: Color @Composable get() = appColors().Danger
    val DangerBg: Color @Composable get() = appColors().DangerBg
}