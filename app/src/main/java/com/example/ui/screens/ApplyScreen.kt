package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.IconDataProvider
import com.example.model.LauncherInfo
import com.example.ui.components.GlassBadge
import com.example.ui.components.LiquidGlassButton
import com.example.ui.components.LiquidGlassCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.IconPackViewModel

@Composable
fun ApplyScreen(
    viewModel: IconPackViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val installedLaunchers by viewModel.installedLaunchers.collectAsState()
    var selectedLauncherForGuide by remember { mutableStateOf<LauncherInfo?>(null) }

    LaunchedEffect(Unit) {
        viewModel.checkInstalledLaunchers()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Apply Banner
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("apply_hero_card"),
                shapeRadius = 24.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(GlassCyan.copy(alpha = 0.3f), GlassViolet.copy(alpha = 0.3f))
                                )
                            )
                            .border(1.5.dp, GlassCyanLight, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "✨", fontSize = 24.sp)
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Apply Icon Pack",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Compatible with Nova, Lawnchair, Niagara, Smart & more",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LiquidGlassButton(
                    text = "✨ QUICK APPLY TO LAUNCHER",
                    onClick = {
                        val firstInstalled = IconDataProvider.supportedLaunchers.firstOrNull {
                            installedLaunchers.contains(it.packageName)
                        }
                        if (firstInstalled != null) {
                            viewModel.applyToLauncher(context, firstInstalled)
                        } else {
                            Toast.makeText(
                                context,
                                "Select your launcher below or tap to install one from Google Play!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("quick_apply_button"),
                    gradientColors = listOf(Color(0xFF0284C7), Color(0xFFA855F7))
                )
            }
        }

        // Section: Installed Launchers (if any)
        val installedList = IconDataProvider.supportedLaunchers.filter { installedLaunchers.contains(it.packageName) }
        if (installedList.isNotEmpty()) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = GlassEmerald,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Installed on this Device",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = GlassEmerald
                    )
                }
            }

            items(installedList) { launcher ->
                LauncherItemRow(
                    launcher = launcher,
                    isInstalled = true,
                    onApply = { viewModel.applyToLauncher(context, launcher) },
                    onShowGuide = { selectedLauncherForGuide = launcher }
                )
            }
        }

        // Section: All Supported Launchers
        item {
            Text(
                text = "Supported Launchers",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        items(IconDataProvider.supportedLaunchers) { launcher ->
            val isInstalled = installedLaunchers.contains(launcher.packageName)
            LauncherItemRow(
                launcher = launcher,
                isInstalled = isInstalled,
                onApply = { viewModel.applyToLauncher(context, launcher) },
                onShowGuide = { selectedLauncherForGuide = launcher }
            )
        }

        // Section: Manual Setup Instructions
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.HelpOutline, contentDescription = null, tint = GlassCyan)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "How to apply in any launcher:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                val steps = listOf(
                    "1. Long press an empty area on your home screen.",
                    "2. Tap 'Home Settings' or 'Launcher Preferences'.",
                    "3. Navigate to 'Look & Feel' or 'Themes & Icons'.",
                    "4. Choose 'Icon Pack' or 'Icon Style'.",
                    "5. Select 'Liquid Glass' from the list."
                )
                steps.forEach { step ->
                    Text(
                        text = step,
                        fontSize = 12.sp,
                        color = TextSecondary,
                        modifier = Modifier.padding(vertical = 3.dp)
                    )
                }
            }
        }
    }

    // Launcher Guide Dialog
    selectedLauncherForGuide?.let { launcher ->
        AlertDialog(
            onDismissRequest = { selectedLauncherForGuide = null },
            containerColor = GlassSurfaceDark,
            title = {
                Text(
                    text = "${launcher.name} Guide",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = "Steps to apply Liquid Glass in ${launcher.name}:",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(GlassSurfaceElevated)
                            .border(1.dp, GlassBorderLight, RoundedCornerShape(12.dp))
                            .padding(14.dp)
                    ) {
                        Text(
                            text = launcher.guideStep,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = GlassCyan
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.applyToLauncher(context, launcher)
                        selectedLauncherForGuide = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GlassCyan)
                ) {
                    Text(text = "Launch Now", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedLauncherForGuide = null }) {
                    Text(text = "Close", color = TextSecondary)
                }
            }
        )
    }
}

@Composable
private fun LauncherItemRow(
    launcher: LauncherInfo,
    isInstalled: Boolean,
    onApply: () -> Unit,
    onShowGuide: () -> Unit
) {
    LiquidGlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("launcher_card_${launcher.id}"),
        shapeRadius = 16.dp,
        backgroundColor = if (isInstalled) GlassSurfaceElevated else GlassSurfaceDark.copy(alpha = 0.7f)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(launcher.accentColor.copy(alpha = 0.15f))
                    .border(1.dp, launcher.accentColor.copy(alpha = 0.40f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = launcher.name.take(2).uppercase(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = launcher.accentColor
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = launcher.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    if (launcher.popularityBadge.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(6.dp))
                        GlassBadge(text = launcher.popularityBadge, accentColor = launcher.accentColor)
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = if (isInstalled) "Installed on device" else "Available on Play Store",
                    fontSize = 11.sp,
                    color = if (isInstalled) GlassEmerald else TextTertiary
                )
            }

            IconButton(
                onClick = onShowGuide,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Guide",
                    tint = TextSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Button(
                onClick = onApply,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isInstalled) GlassCyan else GlassSurfaceElevated
                ),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                modifier = Modifier.height(38.dp)
            ) {
                Text(
                    text = if (isInstalled) "Apply" else "Get",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isInstalled) Color.Black else GlassCyan
                )
            }
        }
    }
}
