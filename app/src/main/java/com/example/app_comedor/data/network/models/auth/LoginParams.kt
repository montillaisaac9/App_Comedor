package com.example.app_comedor.data.network.models.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginParams(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String
)