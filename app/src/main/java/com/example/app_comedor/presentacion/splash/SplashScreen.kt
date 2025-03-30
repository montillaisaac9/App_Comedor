package com.example.app_comedor.presentacion.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun SplashScreen(navController: NavHostController) {
    // Your splash screen UI
    // For example: Logo, animation, etc.

    LaunchedEffect(key1 = true) {
        // Simulate authentication check
        // Replace with your actual authentication logic
        val isLoggedIn = checkIfUserIsLoggedIn()

        // Delay to show splash screen (optional)
        kotlinx.coroutines.delay(1500)

        // Navigate to appropriate destination based on login status
        if (isLoggedIn) {
            navController.navigate("dashboard_graph") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("auth") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }
    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){                 CircularProgressIndicator() }
}

// Sample function to check if user is logged in
private fun checkIfUserIsLoggedIn(): Boolean {
    // Implement your authentication check logic here
    // Example: return dataStore.getToken() != null
    return true // Default to not logged in
}