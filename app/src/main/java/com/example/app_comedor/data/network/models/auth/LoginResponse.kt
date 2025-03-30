package com.example.app_comedor.data.network.models.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id") val id: Int? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("identification") val identification: String? = null,
    @SerialName("role") val role: String? = null,
    @SerialName("securityWord") val securityWord: String? = null,
    @SerialName("isActive") val isActive: Boolean? = null,
    @SerialName("photo") val photo: String? = null,
    @SerialName("careers") val careers: List<Career>? = null
)

@Serializable
data class Career(
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null
)