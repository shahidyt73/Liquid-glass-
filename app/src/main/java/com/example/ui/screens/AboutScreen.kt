package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.GlassBadge
import com.example.ui.components.LiquidGlassCard
import com.example.ui.theme.*

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App Identity Hero
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("about_hero_card"),
                shapeRadius = 24.dp
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(RoundedCornerShape(22.dp))
                            .border(1.5.dp, GlassCyanLight, RoundedCornerShape(22.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_app_icon),
                            contentDescription = "Liquid Glass Logo",
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Liquid Glass Icon Pack",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Version 1.0 Pro • Liquid Glass Studio",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        GlassBadge(text = "Pure Vectors", accentColor = GlassCyan)
                        GlassBadge(text = "AMOLED Black", accentColor = GlassViolet)
                        GlassBadge(text = "Dynamic Calendar", accentColor = GlassEmerald)
                    }
                }
            }
        }

        // Feature Highlights
        item {
            Text(
                text = "Key Specifications",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
                SpecRow(title = "Icon Resolution", value = "192 x 192 (XXXHDPI)")
                HorizontalDivider(color = GlassBorderSubtle, modifier = Modifier.padding(vertical = 8.dp))
                SpecRow(title = "Shader Engine", value = "Multi-Layer Refraction Caustics")
                HorizontalDivider(color = GlassBorderSubtle, modifier = Modifier.padding(vertical = 8.dp))
                SpecRow(title = "Masking Support", value = "Translucent Squircle / Teardrop")
                HorizontalDivider(color = GlassBorderSubtle, modifier = Modifier.padding(vertical = 8.dp))
                SpecRow(title = "Custom Wallpapers", value = "4K Cloud AMOLED Included")
                HorizontalDivider(color = GlassBorderSubtle, modifier = Modifier.padding(vertical = 8.dp))
                SpecRow(title = "Battery Impact", value = "0% (Zero background processes)")
            }
        }

        // FAQ Section
        item {
            Text(
                text = "Frequently Asked Questions",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }

        val faqs = listOf(
            Pair(
                "How do I apply this icon pack?",
                "Open the 'Apply' tab in this app and tap your launcher. If your launcher isn't listed, open your launcher's settings > Look & Feel / Themes > Icon Pack > Select 'Liquid Glass'."
            ),
            Pair(
                "Can I customize or change individual icons?",
                "Yes! In most custom launchers like Nova or Lawnchair, long press any icon on your home screen, tap 'Edit' (or the pencil icon), then tap the icon image and select 'Liquid Glass' to pick any alternative icon from the pack."
            ),
            Pair(
                "Why don't system default launchers show icon packs?",
                "Default launchers from some OEMs (like basic Pixel Launcher or older Android) do not support third-party icon packs. We recommend installing Nova Launcher, Niagara, Lawnchair, or Smart Launcher 6 from the Google Play Store."
            ),
            Pair(
                "How do I request an unthemed app?",
                "Go to the 'Request' tab in this app. It will automatically detect unthemed apps installed on your device. Select the apps you want themed and tap 'Send Request'."
            )
        )

        items(faqs.size) { index ->
            val faq = faqs[index]
            var isExpanded by remember { mutableStateOf(false) }

            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded },
                shapeRadius = 14.dp,
                backgroundColor = GlassSurfaceElevated
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = faq.first,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = GlassCyan
                    )
                }

                AnimatedVisibility(visible = isExpanded) {
                    Column {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = faq.second,
                            fontSize = 12.sp,
                            color = TextSecondary,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SpecRow(title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 13.sp, color = TextSecondary)
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = GlassCyanLight)
    }
}
