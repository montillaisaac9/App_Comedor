package com.example.app_comedor.presentacion.screens.auth.resetPassword.components

data class DataResetPassword(
    val email: String = "",
    val wordSecurity: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    var errMsg : String = "",
)
