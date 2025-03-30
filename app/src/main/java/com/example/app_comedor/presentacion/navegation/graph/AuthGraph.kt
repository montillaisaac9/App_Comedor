package com.example.app_comedor.presentacion.navegation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.app_comedor.presentacion.screens.login.LoginScreen

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(
        startDestination = "login",
        route = "auth"
    ) {
        composable("login") {
            LoginScreen(
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToResetPassword = { navController.navigate("reset_password") },
                onLoginSuccess = {
                    navController.navigate("dashboard_graph") {
                        popUpTo("auth") { inclusive = true }
                    }
                }
            )
        }
    }
}