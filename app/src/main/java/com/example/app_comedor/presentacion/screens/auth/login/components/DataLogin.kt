package com.example.app_comedor.presentacion.screens.auth.login.components

data class DataLogin(
    val email: String = "",
    val password: String = "",
    var errMsgEmail : String = "",
    var errMsgPassword : String = "",
)
