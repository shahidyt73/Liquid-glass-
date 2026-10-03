package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.IconPackViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LiquidGlassTheme {
                val viewModel: IconPackViewModel = viewModel()
                val currentScreen by viewModel.currentScreen.collectAsState()

                // Handle back press: if on a secondary tab, return to ICONS showcase
                BackHandler(enabled = currentScreen != AppScreen.ICONS) {
                    viewModel.navigateTo(AppScreen.ICONS)
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = GlassBackground,
                    contentWindowInsets = WindowInsets.safeDrawing,
                    bottomBar = {
                        LiquidGlassBottomNav(
                            currentScreen = currentScreen,
                            onNavigate = { viewModel.navigateTo(it) }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        AnimatedContent(
                            targetState = currentScreen,
                            transitionSpec = {
                                fadeIn() togetherWith fadeOut()
                            },
                            label = "ScreenTransition"
                        ) { screen ->
                            when (screen) {
                                AppScreen.ICONS -> IconShowcaseScreen(viewModel)
                                AppScreen.APPLY -> ApplyScreen(viewModel)
                                AppScreen.STUDIO -> GlassStudioScreen(viewModel)
                                AppScreen.WALLPAPERS -> WallpapersScreen(viewModel)
                                AppScreen.REQUEST -> IconRequestScreen(viewModel)
                                AppScreen.ABOUT -> AboutScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

data class NavTabItem(
    val screen: AppScreen,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun LiquidGlassBottomNav(
    currentScreen: AppScreen,
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    val navTabs = listOf(
        NavTabItem(AppScreen.ICONS, "Icons", Icons.Filled.GridView, Icons.Outlined.GridView),
        NavTabItem(AppScreen.APPLY, "Apply", Icons.Filled.AutoAwesome, Icons.Outlined.AutoAwesome),
        NavTabItem(AppScreen.STUDIO, "Studio", Icons.Filled.Palette, Icons.Outlined.Palette),
        NavTabItem(AppScreen.WALLPAPERS, "Walls", Icons.Filled.Wallpaper, Icons.Outlined.Wallpaper),
        NavTabItem(AppScreen.REQUEST, "Request", Icons.Filled.Mail, Icons.Outlined.MailOutline),
        NavTabItem(AppScreen.ABOUT, "About", Icons.Filled.Info, Icons.Outlined.Info)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(12.dp, RoundedCornerShape(26.dp), spotColor = Color(0x66000000))
                .clip(RoundedCornerShape(26.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            GlassSurfaceElevated.copy(alpha = 0.95f),
                            GlassSurfaceDark.copy(alpha = 0.98f)
                        )
                    )
                )
                .border(
                    width = 1.2.dp,
                    brush = Brush.horizontalGradient(
                        colors = listOf(GlassBorderLight, GlassBorderSubtle, GlassBorderLight)
                    ),
                    shape = RoundedCornerShape(26.dp)
                )
                .padding(vertical = 6.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navTabs.forEach { tab ->
                val isSelected = currentScreen == tab.screen

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .testTag("nav_tab_${tab.screen.name.lowercase()}")
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onNavigate(tab.screen) }
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .height(30.dp)
                            .then(
                                if (isSelected) {
                                    Modifier
                                        .clip(CircleShape)
                                        .background(GlassCyan.copy(alpha = 0.22f))
                                        .border(1.dp, GlassCyan.copy(alpha = 0.5f), CircleShape)
                                        .padding(horizontal = 12.dp)
                                } else Modifier.padding(horizontal = 4.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                            contentDescription = tab.label,
                            tint = if (isSelected) GlassCyanLight else TextSecondary,
                            modifier = Modifier.size(19.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = tab.label,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color.White else TextTertiary
                    )
                }
            }
        }
    }
}
