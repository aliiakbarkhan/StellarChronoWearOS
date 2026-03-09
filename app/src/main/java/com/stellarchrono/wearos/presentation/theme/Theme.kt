package com.stellarchrono.wearos.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ── Color Palette (matches the Stellar Chrono design) ─────────────

/** Vibrant green accent from the design */
val PrimaryGreen = Color(0xFF46F20D)
val PrimaryGreenDim = Color(0x8846F20D) // 53% opacity
val DarkBackground = Color(0xFF0A0F08)
val SurfaceDark = Color(0xFF1A1F1A)
val ButtonBackground = Color(0xFF2A3029)
val ButtonBorder = Color(0xFF3A4039)
val TextPrimary = Color(0xFFF1F5F0)
val TextSecondary = Color(0xFF8A9488)
val TextMuted = Color(0xFF5A6558)
val DiffPositive = Color(0xFF46F20D)
val DiffNegative = Color(0xFFFF6B6B)

val StellarChronoColors = Colors(
    primary = PrimaryGreen,
    primaryVariant = PrimaryGreenDim,
    secondary = PrimaryGreen,
    secondaryVariant = PrimaryGreenDim,
    background = DarkBackground,
    surface = SurfaceDark,
    error = DiffNegative,
    onPrimary = DarkBackground,
    onSecondary = DarkBackground,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
    onError = Color.White
)

// ── Typography ────────────────────────────────────────────────────

val StellarChronoTypography = Typography(
    display1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        letterSpacing = (-2).sp
    ),
    display2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        letterSpacing = (-1.5).sp
    ),
    display3 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 20.sp,
        letterSpacing = (-0.5).sp
    ),
    title1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        letterSpacing = 3.sp
    ),
    title2 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    title3 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp
    ),
    caption1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 9.sp
    ),
    caption2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 8.sp
    )
)

// ── Theme Composable ──────────────────────────────────────────────

@Composable
fun StellarChronoTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = StellarChronoColors,
        typography = StellarChronoTypography,
        content = content
    )
}
