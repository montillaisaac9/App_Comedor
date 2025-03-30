package com.example.app_comedor.presentacion.navegation.graph

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

// Dashboard Navigation Graph (for logged-in users)
fun NavGraphBuilder.dashboardNavGraph(navController: NavController) {
    navigation(
        startDestination = "dashboard",
        route = "dashboard_graph"
    ) {
        composable("dashboard") {
            DashboardScreen(
                onNavigateToProfile = { navController.navigate("profile") },
                onNavigateToSettings = { navController.navigate("settings") },
                onLogout = {
                    // Navigate back to auth when logging out
                    navController.navigate("auth") {
                        popUpTo("dashboard_graph") { inclusive = true }
                    }
                }
            )
        }
    }
}

// Screen Composables
@Composable
fun DashboardScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onLogout: () -> Unit
) {
    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){     Text("DASHBOARD") }

}