package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LiquidGlassColorScheme = darkColorScheme(
    primary = GlassCyan,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF0C4A6E),
    onPrimaryContainer = GlassCyanLight,
    secondary = GlassViolet,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF581C87),
    onSecondaryContainer = GlassVioletLight,
    tertiary = GlassEmerald,
    onTertiary = Color.Black,
    background = GlassBackground,
    onBackground = TextPrimary,
    surface = GlassSurfaceDark,
    onSurface = TextPrimary,
    surfaceVariant = GlassSurfaceElevated,
    onSurfaceVariant = TextSecondary,
    outline = GlassBorderSubtle,
    outlineVariant = GlassBorderLight
)

@Composable
fun LiquidGlassTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LiquidGlassColorScheme,
        typography = Typography,
        content = content
    )
}
