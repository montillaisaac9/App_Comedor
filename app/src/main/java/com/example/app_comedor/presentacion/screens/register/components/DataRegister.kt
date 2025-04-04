package com.example.app_comedor.presentacion.screens.register.components

data class DataRegister(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val identification: String = "",
    val name: String = "",
    val securityWord: String = "",
    val careerIds: List<Int> = emptyList<Int>(),
    var errMessge : String = "",
)
