package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.data.IconDataProvider
import com.example.model.WallpaperItem
import com.example.ui.components.GlassBadge
import com.example.ui.components.LiquidGlassButton
import com.example.ui.components.LiquidGlassCard
import com.example.ui.components.LiquidGlassIconView
import com.example.ui.theme.*
import com.example.ui.viewmodel.IconPackViewModel

@Composable
fun WallpapersScreen(
    viewModel: IconPackViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val previewWallpaper by viewModel.selectedWallpaperForPreview.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("wallpapers_hero_card"),
                shapeRadius = 24.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(GlassCyan.copy(alpha = 0.2f))
                            .border(1.dp, GlassCyanLight, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🌌", fontSize = 24.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Liquid Wallpapers",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            GlassBadge(text = "4K OLED", accentColor = GlassCyan)
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Crafted to complement translucent glass icons",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Wallpapers 2-column Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(bottom = 96.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(IconDataProvider.curatedWallpapers, key = { it.id }) { item ->
                    WallpaperCard(
                        wallpaper = item,
                        onClick = { viewModel.previewWallpaper(item) }
                    )
                }
            }
        }

        // Fullscreen Interactive Preview Dialog with Glass Icons Overlay
        previewWallpaper?.let { wp ->
            WallpaperFullscreenDialog(
                wallpaper = wp,
                onDismiss = { viewModel.previewWallpaper(null) },
                onApply = {
                    viewModel.setAsDeviceWallpaper(context, wp)
                }
            )
        }
    }
}

@Composable
private fun WallpaperCard(
    wallpaper: WallpaperItem,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .shadow(8.dp, RoundedCornerShape(20.dp), spotColor = Color(0x33000000))
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, GlassBorderLight, RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .testTag("wallpaper_item_${wallpaper.id}")
    ) {
        if (wallpaper.drawableRes != null) {
            Image(
                painter = painterResource(id = wallpaper.drawableRes),
                contentDescription = wallpaper.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(wallpaper.gradientColors)
                    )
            )
        }

        // Gradient overlay for readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0x99000000), Color(0xDD080A10)),
                        startY = 150f
                    )
                )
        )

        // Top tag
        if (wallpaper.isAiGenerated) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopEnd)
            ) {
                GlassBadge(text = "Featured", accentColor = GlassCyan)
            }
        }

        // Bottom title & category
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        ) {
            Text(
                text = wallpaper.name,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${wallpaper.category} • ${wallpaper.resolutionLabel}",
                fontSize = 10.sp,
                color = TextSecondary
            )
        }
    }
}

@Composable
private fun WallpaperFullscreenDialog(
    wallpaper: WallpaperItem,
    onDismiss: () -> Unit,
    onApply: () -> Unit
) {
    var showMockupIcons by remember { mutableStateOf(true) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            // Wallpaper Background
            if (wallpaper.drawableRes != null) {
                Image(
                    painter = painterResource(id = wallpaper.drawableRes),
                    contentDescription = wallpaper.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(wallpaper.gradientColors))
                )
            }

            // Simulated Home Screen Floating Icons
            if (showMockupIcons) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "12:45",
                        fontSize = 56.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                    Text(
                        text = "Tuesday, October 2 • 22°C Clear",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    // Grid of 4 liquid glass sample icons
                    val sampleIcons = IconDataProvider.icons.take(4)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        sampleIcons.forEach { icon ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                LiquidGlassIconView(icon = icon, size = 64.dp)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = icon.name,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            // Top control bar
            Row(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color(0x77000000))
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                }

                Button(
                    onClick = { showMockupIcons = !showMockupIcons },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0x77000000)),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = if (showMockupIcons) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = if (showMockupIcons) "Hide Icons" else "Show Icons", color = Color.White, fontSize = 12.sp)
                }
            }

            // Bottom Apply Action Bar
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(20.dp)
            ) {
                LiquidGlassButton(
                    text = "SET AS WALLPAPER",
                    onClick = {
                        onApply()
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("set_wallpaper_button"),
                    leadingIcon = Icons.Default.Wallpaper,
                    gradientColors = listOf(Color(0xFF0284C7), Color(0xFF10B981))
                )
            }
        }
    }
}
