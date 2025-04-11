package com.example.app_comedor.presentacion.navegation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.app_comedor.presentacion.navegation.destination.Screen
import com.example.app_comedor.presentacion.screens.auth.login.LoginScreen
import com.example.app_comedor.presentacion.screens.auth.register.RegisterScreen

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(
        startDestination = Screen.LoginScreen.route,
        route = "auth"
    ) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
    }
}