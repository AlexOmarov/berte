package ru.somarov.berte.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screen(val info: ScreenInfo) {
    Login(
        ScreenInfo(
            id = "login",
            title = "Login",
            icon = Icons.Outlined.Home,
            root = true,
            fullscreen = true
        )
    ),
    Home(
        ScreenInfo(
            id = "home",
            title = "Home",
            icon = Icons.Outlined.Home,
            root = true,
            fullscreen = false
        )
    ),
    Register(
        ScreenInfo(
            id = "register",
            title = "Register",
            icon = Icons.Outlined.Home,
            root = false,
            fullscreen = false
        )
    ),
    Session(
        ScreenInfo(
            id = "session",
            title = "Session",
            icon = Icons.Outlined.Home,
            root = false,
            fullscreen = false
        )
    ),
}

data class ScreenInfo(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val root: Boolean,
    val fullscreen: Boolean
)
