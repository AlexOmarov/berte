package ru.somarov.berte.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class Route(val info: RouteInfo) {
    Login(
        RouteInfo(
            id = "login",
            title = "Login",
            icon = Icons.Outlined.Home,
            root = true,
            fullscreen = true
        )
    ),
    Home(
        RouteInfo(
            id = "home",
            title = "Home",
            icon = Icons.Outlined.Home,
            root = true,
            fullscreen = false
        )
    ),
    Register(
        RouteInfo(
            id = "register",
            title = "Register",
            icon = Icons.Outlined.Home,
            root = false,
            fullscreen = false
        )
    ),
    Session(
        RouteInfo(
            id = "session",
            title = "Session",
            icon = Icons.Outlined.Home,
            root = false,
            fullscreen = false
        )
    );

    data class RouteInfo(
        val id: String,
        val title: String,
        val icon: ImageVector,
        val root: Boolean,
        val fullscreen: Boolean
    )
}
