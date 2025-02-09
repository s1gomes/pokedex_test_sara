package com.dominio.pokedex.main.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val CustomLightColors = lightColorScheme(
    primary = Color(0xFF03548A),
    secondary = Color(0xFFEBD56F),
    background = Color(0xD9FFFFFF),
    surface = Color(0xFFFFFFFF),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFFC7DFF0),
    onSecondary = Color(0xFF003400),
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF000C4C),
    onError = Color(0xFFDA6565),
)

val CustomDarkColors = darkColorScheme(
    primary = Color(0xFF03548A),
    secondary = Color(0xFFEBD56F),
    background = Color(0xFF989898),
    surface = Color(0xFFFFFFFF),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFF003400),
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF000C4C),
    onError = Color(0xFF810606),
)