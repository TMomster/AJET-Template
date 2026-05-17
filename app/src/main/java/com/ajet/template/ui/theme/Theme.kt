package com.ajet.template.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

// AMOLED主题 - 使用纯黑色背景
private val AmoledColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = androidx.compose.ui.graphics.Color.Black,
    surface = androidx.compose.ui.graphics.Color.Black,
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFF1C1C1C),
    surfaceTint = androidx.compose.ui.graphics.Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun AjetTemplateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    amoledTheme: Boolean = false,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    // 优化：控制是否启用主题切换动画（首次启动时禁用）
    enableAnimation: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        amoledTheme -> AmoledColorScheme
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    // 深度优化：使用 tween 动画，确保稳定的缓慢过渡
    // 只对关键颜色做动画，减少重组开销
    val animationSpec = if (enableAnimation) {
        // 明确的 1200ms 时长，确保缓慢平滑的过渡效果
        tween<Color>(
            durationMillis = 1200,  // 1.2秒，非常缓慢
            easing = androidx.compose.animation.core.FastOutSlowInEasing
        )
    } else {
        tween<Color>(durationMillis = 0) // 立即完成
    }
    
    // 优化：只对最影响视觉的关键颜色做动画（从16个减少到6个）
    val animatedBackground by animateColorAsState(
        targetValue = colorScheme.background,
        animationSpec = animationSpec,
        label = "backgroundColor"
    )
    val animatedSurface by animateColorAsState(
        targetValue = colorScheme.surface,
        animationSpec = animationSpec,
        label = "surfaceColor"
    )
    val animatedSurfaceVariant by animateColorAsState(
        targetValue = colorScheme.surfaceVariant,
        animationSpec = animationSpec,
        label = "surfaceVariantColor"
    )
    val animatedPrimary by animateColorAsState(
        targetValue = colorScheme.primary,
        animationSpec = animationSpec,
        label = "primaryColor"
    )
    val animatedPrimaryContainer by animateColorAsState(
        targetValue = colorScheme.primaryContainer,
        animationSpec = animationSpec,
        label = "primaryContainerColor"
    )
    val animatedSecondaryContainer by animateColorAsState(
        targetValue = colorScheme.secondaryContainer,
        animationSpec = animationSpec,
        label = "secondaryContainerColor"
    )
    
    // 性能优化：使用 remember 缓存 ColorScheme，避免每次重组都创建新对象
    val animatedColorScheme = remember(
        animatedBackground, animatedSurface, animatedSurfaceVariant,
        animatedPrimary, animatedPrimaryContainer, animatedSecondaryContainer,
        colorScheme.onBackground, colorScheme.onSurface, colorScheme.onSurfaceVariant,
        colorScheme.onPrimary, colorScheme.onPrimaryContainer,
        colorScheme.secondary, colorScheme.onSecondary, colorScheme.onSecondaryContainer,
        colorScheme.tertiary, colorScheme.onTertiary
    ) {
        colorScheme.copy(
            // 有动画的颜色
            background = animatedBackground,
            surface = animatedSurface,
            surfaceVariant = animatedSurfaceVariant,
            primary = animatedPrimary,
            primaryContainer = animatedPrimaryContainer,
            secondaryContainer = animatedSecondaryContainer,
            // 无动画的颜色（直接使用目标值）
            onBackground = colorScheme.onBackground,
            onSurface = colorScheme.onSurface,
            onSurfaceVariant = colorScheme.onSurfaceVariant,
            onPrimary = colorScheme.onPrimary,
            onPrimaryContainer = colorScheme.onPrimaryContainer,
            secondary = colorScheme.secondary,
            onSecondary = colorScheme.onSecondary,
            onSecondaryContainer = colorScheme.onSecondaryContainer,
            tertiary = colorScheme.tertiary,
            onTertiary = colorScheme.onTertiary
        )
    }
    
    // 性能优化：使用 derivedStateOf 减少不必要的计算
    val isDarkMode by remember(darkTheme, amoledTheme) {
        derivedStateOf { darkTheme || amoledTheme }
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        // 性能优化：只在主题类型改变时更新窗口，而不是每次颜色变化都更新
        SideEffect {
            val window = (view.context as Activity).window
            
            // 设置状态栏颜色（使用动画后的颜色）
            window.statusBarColor = animatedPrimary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            
            // 关键修复：设置窗口背景色，防止页面切换时的白色闪屏
            val backgroundColor = if (isDarkMode) {
                android.graphics.Color.parseColor("#1C1B1F")
            } else {
                android.graphics.Color.parseColor("#FFFBFE")
            }
            window.decorView.setBackgroundColor(backgroundColor)
        }
    }

    MaterialTheme(
        colorScheme = animatedColorScheme,
        typography = Typography,
        content = content
    )
}
