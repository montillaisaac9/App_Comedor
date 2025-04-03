package com.example.app_comedor.data.network.models.auth

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class CreateUser(
    @SerialName("email")
    val email: String,

    @SerialName("identification")
    val identification: String,

    @SerialName("name")
    val name: String,

    @SerialName("password")
    val password: String,

    @SerialName("securityWord")
    val securityWord: String,

    @SerialName("role")
    val role: String, // Cambié Role a String porque en Kotlin deberás mapearlo con un Enum manualmente

    @SerialName("position")
    val position: String? = null,

    @SerialName("photo")
    val photo: String? = null,

    @SerialName("isActive")
    val isActive: Boolean? = null,

    @SerialName("careerIds")
    val careerIds: List<Int>
)
