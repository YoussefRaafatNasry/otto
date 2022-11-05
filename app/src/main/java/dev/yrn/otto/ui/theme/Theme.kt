package dev.yrn.otto.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable


private val DarkColorPalette = darkColors(
    primary = White,
    primaryVariant = White,
    secondary = White,
    background = Black
)

@Composable
fun OttoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}