import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

// Light Colors
val LightSpaceThemeColors = lightColors(
    primary = Color(0xFF1A237E), // Deep Space Blue
    primaryVariant = Color(0xFF0D47A1), // Interstellar Blue
    onPrimary = Color.White,
    secondary = Color(0xFFFF6F00), // Nebula Orange
    secondaryVariant = Color(0xFFFFA000), // Starburst Orange
    onSecondary = Color.White,
    background = Color(0xFFFFFFFF), // White
    onBackground = Color.Black,
    surface = Color(0xFFFAFAFA), // Light Gray
    onSurface = Color.Black,
    error = Color(0xFFD32F2F), // Comet Red
    onError = Color.White
)

// Dark Colors
val DarkSpaceThemeColors = darkColors(
    primary = Color(0xFF1A237E), // Deep Space Blue
    primaryVariant = Color(0xFF0D47A1), // Interstellar Blue
    onPrimary = Color.White,
    secondary = Color(0xFFFF6F00), // Nebula Orange
    secondaryVariant = Color(0xFFFFA000), // Starburst Orange
    onSecondary = Color.White,
    background = Color(0xFF121212), // Space Black
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E), // Asteroid Grey
    onSurface = Color.White,
    error = Color(0xFFD32F2F), // Comet Red
    onError = Color.White
)