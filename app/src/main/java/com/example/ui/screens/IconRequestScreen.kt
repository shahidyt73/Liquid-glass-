package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.GlassBadge
import com.example.ui.components.LiquidGlassButton
import com.example.ui.components.LiquidGlassCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.IconPackViewModel
import com.example.ui.viewmodel.InstalledAppItem

@Composable
fun IconRequestScreen(
    viewModel: IconPackViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val installedApps by viewModel.installedApps.collectAsState()
    val isScanning by viewModel.isScanning.collectAsState()
    val selectedMissingApps by viewModel.selectedMissingApps.collectAsState()

    LaunchedEffect(Unit) {
        if (installedApps.isEmpty()) {
            viewModel.scanDeviceApps()
        }
    }

    val themedCount = remember(installedApps) { installedApps.count { it.isThemed } }
    val unthemedCount = remember(installedApps) { installedApps.count { !it.isThemed } }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header stats
            item {
                LiquidGlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("request_hero_card"),
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
                                .background(GlassEmerald.copy(alpha = 0.2f))
                                .border(1.dp, GlassEmerald, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "📬", fontSize = 24.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Icon Request Tool",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Request liquid glass styling for unthemed apps",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Progress & Breakdown
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        StatBox(
                            label = "Themed",
                            count = "$themedCount",
                            color = GlassEmerald,
                            modifier = Modifier.weight(1f)
                        )
                        StatBox(
                            label = "Missing",
                            count = "$unthemedCount",
                            color = GlassCyan,
                            modifier = Modifier.weight(1f)
                        )
                        StatBox(
                            label = "Selected",
                            count = "${selectedMissingApps.size}",
                            color = GlassViolet,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Actions row: Select All / Clear / Refresh
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            TextButton(
                                onClick = { viewModel.selectAllMissingApps() },
                                colors = ButtonDefaults.textButtonColors(contentColor = GlassCyan)
                            ) {
                                Text("Select All Missing", fontSize = 12.sp)
                            }
                            if (selectedMissingApps.isNotEmpty()) {
                                TextButton(
                                    onClick = { viewModel.clearMissingAppsSelection() },
                                    colors = ButtonDefaults.textButtonColors(contentColor = TextSecondary)
                                ) {
                                    Text("Clear", fontSize = 12.sp)
                                }
                            }
                        }

                        IconButton(
                            onClick = { viewModel.scanDeviceApps() },
                            modifier = Modifier.size(36.dp)
                        ) {
                            if (isScanning) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(18.dp),
                                    strokeWidth = 2.dp,
                                    color = GlassCyan
                                )
                            } else {
                                Icon(Icons.Default.Refresh, contentDescription = "Rescan", tint = TextSecondary)
                            }
                        }
                    }
                }
            }

            // List of unthemed apps
            item {
                Text(
                    text = "Apps on Your Device",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }

            if (installedApps.isEmpty() && isScanning) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = GlassCyan)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = "Scanning installed applications...", color = TextSecondary, fontSize = 13.sp)
                        }
                    }
                }
            }

            items(installedApps, key = { it.packageName }) { app ->
                AppRequestRow(
                    app = app,
                    isSelected = selectedMissingApps.contains(app.packageName),
                    onToggle = { viewModel.toggleMissingAppSelection(app.packageName) }
                )
            }
        }

        // Bottom floating request button
        AnimatedVisibility(
            visible = selectedMissingApps.isNotEmpty(),
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            LiquidGlassButton(
                text = "SEND REQUEST (${selectedMissingApps.size} APPS)",
                onClick = { viewModel.sendIconRequest(context) },
                leadingIcon = Icons.Default.Send,
                gradientColors = listOf(Color(0xFF0284C7), Color(0xFF10B981)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("send_request_button")
            )
        }
    }
}

@Composable
private fun StatBox(
    label: String,
    count: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.12f))
            .border(1.dp, color.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = count, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color)
            Text(text = label, fontSize = 11.sp, color = TextSecondary)
        }
    }
}

@Composable
private fun AppRequestRow(
    app: InstalledAppItem,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    LiquidGlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("app_request_row_${app.packageName}")
            .clickable(enabled = !app.isThemed, onClick = onToggle),
        shapeRadius = 14.dp,
        backgroundColor = if (isSelected) GlassSurfaceElevated else GlassSurfaceDark.copy(alpha = 0.7f),
        borderColor = if (isSelected) GlassCyan else GlassBorderSubtle
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (app.isThemed) GlassEmerald.copy(alpha = 0.15f) else GlassSurfaceDark)
                    .border(
                        1.dp,
                        if (app.isThemed) GlassEmerald.copy(alpha = 0.4f) else GlassBorderSubtle,
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = app.label.take(1).uppercase(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (app.isThemed) GlassEmerald else GlassCyan
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = app.label,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = app.packageName,
                    fontSize = 10.sp,
                    color = TextTertiary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (app.isThemed) {
                GlassBadge(text = "Themed", accentColor = GlassEmerald)
            } else {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = { onToggle() },
                    colors = CheckboxDefaults.colors(
                        checkedColor = GlassCyan,
                        uncheckedColor = GlassBorderLight
                    )
                )
            }
        }
    }
}
