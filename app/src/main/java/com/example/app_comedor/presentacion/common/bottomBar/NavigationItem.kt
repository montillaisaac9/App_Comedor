package com.example.app_comedor.presentacion.common.bottomBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FoodBank
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.app_comedor.presentacion.navegation.destination.Screen

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

val navigationItems = listOf(
    NavigationItem(
        title = "Menu",
        icon = Icons.Outlined.FoodBank,
        route = Screen.MenuScreen.route
    ),
    NavigationItem(
        title = "Profile",
        icon = Icons.Outlined.PersonOutline,
        route = Screen.ProfileScreen.route
    )
)