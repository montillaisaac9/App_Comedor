package com.example.app_comedor.presentacion.navegation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_comedor.presentacion.splash.SplashScreen

@Composable
fun RootNavigationGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier
    ) {
        // Splash screen to check authentication status
        composable("splash") {
            SplashScreen(navController = navController)
        }

        // Include the authentication graph
        authNavGraph(navController)

        // Include the dashboard graph
        dashboardNavGraph(navController)
    }
}