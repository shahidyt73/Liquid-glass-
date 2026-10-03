package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.IconCategory
import com.example.model.IconItem
import com.example.ui.components.GlassBadge
import com.example.ui.components.LiquidGlassCard
import com.example.ui.components.LiquidGlassIconView
import com.example.ui.theme.*
import com.example.ui.viewmodel.IconPackViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconShowcaseScreen(
    viewModel: IconPackViewModel,
    modifier: Modifier = Modifier
) {
    val icons by viewModel.filteredIcons.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val isCompactGrid by viewModel.isCompactGrid.collectAsState()
    val inspectingIcon by viewModel.inspectingIcon.collectAsState()

    val context = LocalContext.current

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Hero Header Card
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "🫧 Liquid Glass",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            GlassBadge(text = "v1.0 Pro", accentColor = GlassCyan)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Ultra-premium refractive frosted icons",
                            fontSize = 13.sp,
                            color = TextSecondary
                        )
                    }

                    // Grid density toggle button
                    IconButton(
                        onClick = { viewModel.toggleGridDensity() },
                        modifier = Modifier
                            .testTag("toggle_density_button")
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(GlassSurfaceElevated)
                            .border(1.dp, GlassBorderLight, CircleShape)
                    ) {
                        Icon(
                            imageVector = if (isCompactGrid) Icons.Default.GridView else Icons.Default.ViewModule,
                            contentDescription = "Toggle Grid Density",
                            tint = GlassCyan
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Stats row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatPill(
                        label = "Icons",
                        value = "${icons.size}",
                        color = GlassCyan,
                        modifier = Modifier.weight(1f)
                    )
                    StatPill(
                        label = "Resolution",
                        value = "192px HD",
                        color = GlassViolet,
                        modifier = Modifier.weight(1f)
                    )
                    StatPill(
                        label = "Shaders",
                        value = "Glass Caustic",
                        color = GlassEmerald,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setSearchQuery(it) },
                placeholder = { Text("Search by app or keyword...", color = TextTertiary, fontSize = 14.sp) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = GlassCyan)
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.setSearchQuery("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear", tint = TextSecondary)
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = GlassSurfaceDark,
                    unfocusedContainerColor = GlassSurfaceDark.copy(alpha = 0.6f),
                    focusedBorderColor = GlassCyan,
                    unfocusedBorderColor = GlassBorderSubtle,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("icon_search_input")
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Category Filter Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(IconCategory.entries.toTypedArray()) { cat ->
                    val isSelected = cat == selectedCategory
                    Box(
                        modifier = Modifier
                            .testTag("filter_chip_${cat.name.lowercase()}")
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                if (isSelected) Brush.horizontalGradient(
                                    listOf(GlassCyan.copy(alpha = 0.35f), GlassViolet.copy(alpha = 0.35f))
                                ) else Brush.linearGradient(
                                    listOf(GlassSurfaceElevated, GlassSurfaceDark)
                                )
                            )
                            .border(
                                width = 1.dp,
                                color = if (isSelected) GlassCyan else GlassBorderSubtle,
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clickable { viewModel.selectCategory(cat) }
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = cat.displayName,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Color.White else TextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Icons Grid
            val columns = if (isCompactGrid) 5 else 4
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                contentPadding = PaddingValues(bottom = 96.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("icons_grid")
            ) {
                items(icons, key = { it.id }) { iconItem ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .testTag("icon_cell_${iconItem.id}")
                            .clickable { viewModel.inspectIcon(iconItem) }
                            .padding(vertical = 4.dp)
                    ) {
                        LiquidGlassIconView(
                            icon = iconItem,
                            size = if (isCompactGrid) 54.dp else 66.dp,
                            onClick = { viewModel.inspectIcon(iconItem) }
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = iconItem.name,
                            fontSize = if (isCompactGrid) 11.sp else 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // Glass Inspector Bottom Sheet / Dialog
        inspectingIcon?.let { icon ->
            GlassInspectorModal(
                icon = icon,
                onDismiss = { viewModel.inspectIcon(null) },
                onCopyComponent = {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboard.setPrimaryClip(ClipData.newPlainText("Component", icon.componentName))
                    Toast.makeText(context, "Component copied to clipboard!", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

@Composable
private fun StatPill(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.12f))
            .border(1.dp, color.copy(alpha = 0.30f), RoundedCornerShape(12.dp))
            .padding(vertical = 8.dp, horizontal = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = color)
            Text(text = label, fontSize = 10.sp, color = TextSecondary)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlassInspectorModal(
    icon: IconItem,
    onDismiss: () -> Unit,
    onCopyComponent: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = GlassSurfaceDark,
        tonalElevation = 16.dp,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .width(44.dp)
                    .height(4.dp)
                    .clip(CircleShape)
                    .background(GlassBorderLight)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .padding(bottom = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // High-resolution 110dp preview
            Box(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .size(110.dp),
                contentAlignment = Alignment.Center
            ) {
                LiquidGlassIconView(
                    icon = icon,
                    size = 100.dp,
                    showInteractiveShine = true
                )
            }

            Text(
                text = icon.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            GlassBadge(
                text = icon.category.displayName,
                accentColor = icon.accentColor
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = icon.description,
                fontSize = 13.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Component Info Card
            LiquidGlassCard(
                modifier = Modifier.fillMaxWidth(),
                shapeRadius = 14.dp,
                backgroundColor = GlassSurfaceElevated
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Component Info",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = GlassCyan
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = icon.componentName,
                            fontSize = 11.sp,
                            color = TextSecondary,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    IconButton(
                        onClick = onCopyComponent,
                        modifier = Modifier
                            .testTag("copy_component_button")
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(GlassSurfaceDark)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy Component",
                            tint = GlassCyanLight,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onDismiss,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GlassSurfaceElevated
                ),
                border = ButtonDefaults.outlinedButtonBorder,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Close Preview", color = TextPrimary)
            }
        }
    }
}
