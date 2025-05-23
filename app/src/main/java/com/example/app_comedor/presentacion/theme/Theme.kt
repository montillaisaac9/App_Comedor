package com.example.app_comedor.presentacion.theme

import Typography
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = skyBlue,
    onPrimary= white,
    secondary = softPeach,
    background = darkGray,
    onBackground = white,
    tertiary = mediumGray,
    onTertiaryContainer = white,
    onSecondaryContainer = darkGray
)

private val LightColorScheme = lightColorScheme(
    primary = deepBlue,
    onPrimary= pureBlack,
    secondary = darkPeach,
    background = white,
    onBackground = pureBlack,
    tertiary = lightGray,
    onTertiaryContainer = kingBlue,
    onSecondaryContainer = white
)

@Composable
fun AppComedorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) DarkColorScheme else LightColorScheme
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}