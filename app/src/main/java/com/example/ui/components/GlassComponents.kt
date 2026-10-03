package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GlassShape
import com.example.model.IconItem
import com.example.ui.theme.*

@Composable
fun LiquidGlassIconView(
    icon: IconItem,
    size: Dp = 68.dp,
    shape: GlassShape = GlassShape.SQUIRCLE,
    showInteractiveShine: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    var touchX by remember { mutableFloatStateOf(0.5f) }
    var touchY by remember { mutableFloatStateOf(0.5f) }

    // Subtle breathing ambient refraction
    val infiniteTransition = rememberInfiniteTransition(label = "glassShimmer")
    val shimmerPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmerPhase"
    )

    val shapeCornerRatio = when (shape) {
        GlassShape.SQUIRCLE -> 0.38f
        GlassShape.PEBBLE -> 0.48f
        GlassShape.CIRCLE -> 0.50f
        GlassShape.TEARDROP -> 0.30f
        GlassShape.HEXAGON -> 0.25f
    }

    Box(
        modifier = modifier
            .size(size)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(percent = (shapeCornerRatio * 100).toInt()),
                spotColor = icon.accentColor.copy(alpha = 0.45f)
            )
            .then(
                if (onClick != null) {
                    Modifier
                        .clip(RoundedCornerShape(percent = (shapeCornerRatio * 100).toInt()))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(color = icon.accentColor),
                            onClick = onClick
                        )
                } else Modifier
            )
            .then(
                if (showInteractiveShine) {
                    Modifier.pointerInput(Unit) {
                        detectTapGestures(
                            onPress = { offset ->
                                touchX = (offset.x / size.toPx()).coerceIn(0f, 1f)
                                touchY = (offset.y / size.toPx()).coerceIn(0f, 1f)
                                tryAwaitRelease()
                                touchX = 0.5f
                                touchY = 0.5f
                            }
                        )
                    }
                } else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        if (icon.drawableRes != null) {
            // High-fidelity vector drawable from resources
            Icon(
                painter = painterResource(id = icon.drawableRes),
                contentDescription = icon.name,
                tint = Color.Unspecified,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Dynamic liquid glass multi-layer canvas rendering
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = this.size.width
                val h = this.size.height
                val cornerRadius = CornerRadius(w * shapeCornerRatio, h * shapeCornerRatio)

                // 1. Ambient Caustic Glass Base
                drawRoundRect(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            icon.colorStart.copy(alpha = 0.65f),
                            icon.colorEnd.copy(alpha = 0.85f),
                            Color(0xFF090D16).copy(alpha = 0.95f)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(w, h)
                    ),
                    cornerRadius = cornerRadius
                )

                // 2. Gloss Specular Reflection Crescent (Top Arc)
                val reflectionPath = Path().apply {
                    moveTo(w * 0.15f, h * 0.08f)
                    lineTo(w * 0.85f, h * 0.08f)
                    cubicTo(w * 0.92f, h * 0.28f, w * 0.70f, h * 0.48f, w * 0.5f, h * 0.48f)
                    cubicTo(w * 0.30f, h * 0.48f, w * 0.08f, h * 0.28f, w * 0.15f, h * 0.08f)
                    close()
                }
                drawPath(
                    path = reflectionPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.35f + shimmerPhase * 0.15f),
                            Color.White.copy(alpha = 0.08f),
                            Color.Transparent
                        ),
                        startY = 0f,
                        endY = h * 0.5f
                    )
                )

                // 3. Dynamic Interactive Refractive Glint
                val glintCenter = Offset(
                    w * (0.25f + touchX * 0.5f),
                    h * (0.20f + touchY * 0.4f)
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.30f),
                            icon.accentColor.copy(alpha = 0.15f),
                            Color.Transparent
                        ),
                        center = glintCenter,
                        radius = w * 0.35f
                    ),
                    radius = w * 0.35f,
                    center = glintCenter
                )

                // 4. Refractive Glass Edge Highlight Rim (Directional gradient stroke)
                drawRoundRect(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.85f),
                            icon.accentColor.copy(alpha = 0.70f),
                            Color.White.copy(alpha = 0.15f),
                            icon.accentColor.copy(alpha = 0.40f)
                        ),
                        start = Offset(w * 0.1f, 0f),
                        end = Offset(w * 0.9f, h)
                    ),
                    cornerRadius = cornerRadius,
                    style = Stroke(width = 1.8.dp.toPx())
                )
            }

            // Central Glyph Icon
            if (icon.glyphEmoji.isNotEmpty()) {
                Text(
                    text = icon.glyphEmoji,
                    fontSize = (size.value * 0.42f).sp
                )
            }
        }
    }
}

@Composable
fun LiquidGlassCard(
    modifier: Modifier = Modifier,
    shapeRadius: Dp = 20.dp,
    backgroundColor: Color = GlassSurfaceDark.copy(alpha = 0.80f),
    borderColor: Color = GlassBorderLight,
    borderWidth: Dp = 1.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .shadow(8.dp, RoundedCornerShape(shapeRadius), spotColor = Color(0x33000000))
            .clip(RoundedCornerShape(shapeRadius))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        backgroundColor,
                        GlassSurfaceElevated.copy(alpha = 0.65f)
                    )
                )
            )
            .border(
                width = borderWidth,
                brush = Brush.linearGradient(
                    colors = listOf(
                        borderColor,
                        GlassBorderSubtle,
                        borderColor.copy(alpha = 0.15f)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(400f, 400f)
                ),
                shape = RoundedCornerShape(shapeRadius)
            )
            .padding(16.dp),
        content = content
    )
}

@Composable
fun LiquidGlassButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    gradientColors: List<Color> = listOf(Color(0xFF0284C7), Color(0xFF7C3AED)),
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color(0x22FFFFFF)
        ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
        modifier = modifier
            .heightIn(min = 52.dp)
            .shadow(10.dp, RoundedCornerShape(28.dp), spotColor = gradientColors.first().copy(alpha = 0.5f))
            .clip(RoundedCornerShape(28.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = if (enabled) gradientColors else listOf(Color(0xFF334155), Color(0xFF1E293B))
                )
            )
            .border(
                width = 1.2.dp,
                brush = Brush.horizontalGradient(
                    colors = listOf(Color.White.copy(alpha = 0.7f), Color.White.copy(alpha = 0.15f))
                ),
                shape = RoundedCornerShape(28.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
fun GlassBadge(
    text: String,
    accentColor: Color = GlassCyan,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(accentColor.copy(alpha = 0.15f))
            .border(1.dp, accentColor.copy(alpha = 0.40f), CircleShape)
            .padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = accentColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
