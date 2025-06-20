package com.example.app_comedor.data.network.models.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(
    @SerialName("email")
    val email: String,

    @SerialName("securityWord")
    val securityWord: String,

    @SerialName("newPassword")
    val newPassword: String
)
