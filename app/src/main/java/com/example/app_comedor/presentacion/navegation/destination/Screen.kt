package com.example.app_comedor.presentacion.navegation.destination

sealed class Screen(val route:String) {
    data object LoginScreen: Screen("login_Screen")
    data object RegisterScreen: Screen("register_Screen")
    data object ResetPasswordScreen: Screen("resetPassword_Screen")
    data object SplashScreen: Screen("splash_Screen")
    data object MenuScreen: Screen("Menu_Screen")
    data object ProfileScreen: Screen("Profile_Screen")
}