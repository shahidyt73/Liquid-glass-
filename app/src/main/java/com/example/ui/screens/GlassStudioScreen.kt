package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.IconDataProvider
import com.example.model.GlassShape
import com.example.model.GlassStyleOption
import com.example.model.IconCategory
import com.example.model.IconItem
import com.example.ui.components.GlassBadge
import com.example.ui.components.LiquidGlassButton
import com.example.ui.components.LiquidGlassCard
import com.example.ui.components.LiquidGlassIconView
import com.example.ui.theme.*
import com.example.ui.viewmodel.IconPackViewModel

@Composable
fun GlassStudioScreen(
    viewModel: IconPackViewModel,
    modifier: Modifier = Modifier
) {
    val currentShape by viewModel.studioShape.collectAsState()
    val currentStyle by viewModel.studioStyle.collectAsState()
    val currentOpacity by viewModel.studioOpacity.collectAsState()
    val currentRefraction by viewModel.studioRefraction.collectAsState()
    val currentGlyph by viewModel.studioGlyph.collectAsState()
    val savedIcons by viewModel.customIcons.collectAsState()

    var customTitle by remember { mutableStateOf("") }

    val sampleGlyphs = listOf(
        "🫧", "💎", "⚡", "✨", "🌊", "🔮", "🔥", "🪐",
        "🎯", "🚀", "👑", "🎧", "📷", "💡", "🎨", "🛡️",
        "🧭", "❤️", "⭐", "🎮"
    )

    // Synthesized dynamic preview item
    val previewIconItem = remember(currentStyle, currentGlyph, currentOpacity, currentRefraction) {
        IconItem(
            id = "custom_preview",
            name = customTitle.ifBlank { "Custom Glass" },
            category = IconCategory.TOOLS,
            componentName = "com.custom.liquidglass",
            colorStart = currentStyle.colorStart.copy(alpha = currentOpacity),
            colorEnd = currentStyle.colorEnd.copy(alpha = currentOpacity * 0.9f),
            accentColor = currentStyle.rimHighlight,
            glyphEmoji = currentGlyph,
            description = "Custom refractive liquid glass icon"
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Studio Header
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("studio_hero_card"),
                shapeRadius = 24.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "🎨 Glass Studio",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            GlassBadge(text = "Generator", accentColor = GlassViolet)
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Design, tweak and preview custom glass icons",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Interactive Live Preview
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    currentStyle.glowColor.copy(alpha = 0.25f),
                                    Color(0xFF090C14)
                                )
                            )
                        )
                        .border(1.dp, GlassBorderLight, RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    LiquidGlassIconView(
                        icon = previewIconItem,
                        size = 110.dp,
                        shape = currentShape,
                        showInteractiveShine = true
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Tap & drag on the icon above to inspect real-time glass caustics",
                    fontSize = 11.sp,
                    color = TextTertiary,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

        // Shape Picker
        item {
            Text(
                text = "Glass Shape",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(GlassShape.entries.toTypedArray()) { shape ->
                    val isSelected = shape == currentShape
                    Box(
                        modifier = Modifier
                            .testTag("shape_option_${shape.name.lowercase()}")
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (isSelected) GlassCyan.copy(alpha = 0.25f) else GlassSurfaceDark)
                            .border(1.dp, if (isSelected) GlassCyan else GlassBorderSubtle, RoundedCornerShape(14.dp))
                            .clickable { viewModel.setStudioShape(shape) }
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = shape.displayName,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Color.White else TextSecondary
                        )
                    }
                }
            }
        }

        // Style Picker
        item {
            Text(
                text = "Glass Theme & Material",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(IconDataProvider.glassStyles) { style ->
                    val isSelected = style.id == currentStyle.id
                    Box(
                        modifier = Modifier
                            .testTag("style_option_${style.id}")
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(style.colorStart, style.colorEnd)
                                )
                            )
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) style.rimHighlight else GlassBorderSubtle,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable { viewModel.setStudioStyle(style) }
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = style.name,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // Glyph Selection
        item {
            Text(
                text = "Center Emblem Glyph",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(sampleGlyphs) { glyph ->
                    val isSelected = glyph == currentGlyph
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) GlassViolet.copy(alpha = 0.35f) else GlassSurfaceDark)
                            .border(1.dp, if (isSelected) GlassVioletLight else GlassBorderSubtle, CircleShape)
                            .clickable { viewModel.setStudioGlyph(glyph) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = glyph, fontSize = 20.sp)
                    }
                }
            }
        }

        // Sliders (Opacity & Refraction)
        item {
            LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Refractive Density: ${(currentRefraction * 100).toInt()}%",
                    fontSize = 13.sp,
                    color = TextPrimary
                )
                Slider(
                    value = currentRefraction,
                    onValueChange = { viewModel.setStudioRefraction(it) },
                    valueRange = 0.2f..1.0f,
                    colors = SliderDefaults.colors(
                        thumbColor = GlassCyan,
                        activeTrackColor = GlassCyan
                    ),
                    modifier = Modifier.testTag("slider_refraction")
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Glass Translucency: ${(currentOpacity * 100).toInt()}%",
                    fontSize = 13.sp,
                    color = TextPrimary
                )
                Slider(
                    value = currentOpacity,
                    onValueChange = { viewModel.setStudioOpacity(it) },
                    valueRange = 0.2f..1.0f,
                    colors = SliderDefaults.colors(
                        thumbColor = GlassViolet,
                        activeTrackColor = GlassViolet
                    ),
                    modifier = Modifier.testTag("slider_opacity")
                )
            }
        }

        // Save Custom Icon
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = customTitle,
                    onValueChange = { customTitle = it },
                    placeholder = { Text("Name your custom icon (e.g. My Opal Glass)", color = TextTertiary, fontSize = 13.sp) },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = GlassSurfaceDark,
                        unfocusedContainerColor = GlassSurfaceDark.copy(alpha = 0.6f),
                        focusedBorderColor = GlassViolet,
                        unfocusedBorderColor = GlassBorderSubtle,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("custom_icon_name_input")
                )

                LiquidGlassButton(
                    text = "SAVE TO STUDIO COLLECTION",
                    onClick = {
                        viewModel.saveCustomGlassIcon(customTitle)
                        customTitle = ""
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("save_custom_icon_button"),
                    gradientColors = listOf(Color(0xFF7C3AED), Color(0xFFC026D3))
                )
            }
        }

        // Saved Custom Icons Section
        if (savedIcons.isNotEmpty()) {
            item {
                Text(
                    text = "Your Saved Custom Icons (${savedIcons.size})",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            items(savedIcons) { item ->
                LiquidGlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("saved_icon_card_${item.id}"),
                    backgroundColor = GlassSurfaceElevated
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(GlassSurfaceDark)
                                .border(1.dp, GlassBorderLight, RoundedCornerShape(14.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = item.glyphName, fontSize = 22.sp)
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${item.styleName} • ${item.shapeName}",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }

                        IconButton(
                            onClick = { viewModel.deleteCustomIcon(item.id) },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.DeleteOutline,
                                contentDescription = "Delete",
                                tint = GlassPink
                            )
                        }
                    }
                }
            }
        }
    }
}
