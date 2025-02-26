package com.rayadev.xchange.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Paleta de colores
val BluePrimary = Color(0xFF007BFF) // Azul principal
val BlueSecondary = Color(0xFF0056b3) // Azul secundario (más oscuro)
val BlueTertiary = Color(0xFF003366) // Azul terciario (más oscuro para fondos)
val BlueLight = Color(0xFF66B2FF) // Azul claro para resaltar
val BlueLighter = Color(0xFFE6F0FF) // Azul muy claro para fondos

val White = Color(0xFFFFFFFF) // Blanco
val LightGray = Color(0xFFF0F0F0) // Gris claro para fondos
val MediumGray = Color(0xFFCCCCCC) // Gris medio para bordes
val DarkGray = Color(0xFF333333) // Gris oscuro para texto
val Black = Color(0xFF000000) // Negro

val AccentOrange = Color(0xFFFFA500) // Naranja para resaltar
val SuccessGreen = Color(0xFF4CAF50) // Verde para éxito
val ErrorRed = Color(0xFFD32F2F) // Rojo para errores
val WarningYellow = Color(0xFFFFC107) // Amarillo para advertencias

// Tipografía personalizada
val CustomTypography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        color = DarkGray
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = DarkGray
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = DarkGray
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        color = DarkGray
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = DarkGray
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = DarkGray
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = DarkGray
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = DarkGray
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = DarkGray
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = DarkGray
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = DarkGray
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = DarkGray
    )
)

// Esquema de colores personalizado
val CustomColorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = BlueSecondary,
    tertiary = BlueTertiary,
    background = White,
    surface = LightGray,
    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
    onSurface = DarkGray,
    onBackground = DarkGray,
    surfaceVariant = BlueLighter,
    outline = MediumGray,
    error = ErrorRed,
    errorContainer = ErrorRed.copy(alpha = 0.2f),
    onError = White,
    onErrorContainer = ErrorRed,
)

// Tema personalizado
@Composable
fun CustomTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CustomColorScheme,
        typography = CustomTypography,
        content = content
    )
}