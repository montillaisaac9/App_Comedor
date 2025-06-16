package com.example.app_comedor.presentacion.navegation.graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.app_comedor.presentacion.navegation.destination.Screen
import com.example.app_comedor.presentacion.screens.dish.DishScreen
import com.example.app_comedor.presentacion.screens.menu.MenuScreen
import com.example.app_comedor.presentacion.screens.profile.ProfileScreen

// Dashboard Navigation Graph (for logged-in users)
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.dashboardNavGraph(navController: NavController) {
    navigation(
        startDestination = Screen.MenuScreen.route,
        route = "dashboard_graph"
    ) {
        composable(Screen.MenuScreen.route) {
            MenuScreen(navController)
        }
        composable(Screen.ProfileScreen.route) {
            ProfileScreen(navController)
        }
        composable("Dish_Screen/{id}/{id2}") { backStackEntry ->
            val dishId = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            val menuId = backStackEntry.arguments?.getString("id2")?.toInt() ?: 0
            DishScreen(navController, dishId, menuId)
        }
    }
}