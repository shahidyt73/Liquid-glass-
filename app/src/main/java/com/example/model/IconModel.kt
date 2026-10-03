package com.example.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.ui.theme.*

enum class IconCategory(val displayName: String) {
    ALL("All Icons"),
    SYSTEM("System"),
    COMMUNICATION("Communication"),
    MEDIA("Media & Entertainment"),
    TOOLS("Tools & Utilities"),
    PRODUCTIVITY("Productivity"),
    GOOGLE_SOCIAL("Social & Web"),
    LIFESTYLE("Lifestyle")
}

data class IconItem(
    val id: String,
    val name: String,
    val category: IconCategory,
    @DrawableRes val drawableRes: Int? = null,
    val componentName: String,
    val colorStart: Color,
    val colorEnd: Color,
    val accentColor: Color,
    val glyphEmoji: String = "",
    val glyphSymbolName: String = "",
    val description: String = ""
)

data class LauncherInfo(
    val id: String,
    val name: String,
    val packageName: String,
    val playStoreUrl: String,
    val isDirectApplySupported: Boolean,
    val accentColor: Color,
    val popularityBadge: String = "",
    val guideStep: String
)

data class WallpaperItem(
    val id: String,
    val name: String,
    val category: String,
    @DrawableRes val drawableRes: Int? = null,
    val gradientColors: List<Color> = emptyList(),
    val isAiGenerated: Boolean = false,
    val resolutionLabel: String = "4K AMOLED"
)

enum class GlassShape(val displayName: String) {
    SQUIRCLE("Squircle"),
    PEBBLE("Pebble"),
    CIRCLE("Circle"),
    TEARDROP("Teardrop"),
    HEXAGON("Hex Glass")
}

data class GlassStyleOption(
    val id: String,
    val name: String,
    val colorStart: Color,
    val colorEnd: Color,
    val rimHighlight: Color,
    val glowColor: Color
)
