package com.example.app_comedor.presentacion.navegation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_comedor.presentacion.navegation.destination.Screen
import com.example.app_comedor.presentacion.screens.auth.splash.SplashScreen

@Composable
fun RootNavigationGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        modifier = modifier
    ) {
        // Splash screen to check authentication status
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        // Include the authentication graph
        authNavGraph(navController)

        // Include the dashboard graph
        dashboardNavGraph(navController)
    }
}