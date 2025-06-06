package com.example.app_comedor.data.network.models.dish

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScoreResponse(
    @SerialName("id")
    val id: Int,

    @SerialName("rating")
    val rating: Double,

    @SerialName("userId")
    val userId: Int,

    @SerialName("dishId")
    val dishId: Int,

    @SerialName("createdAt")
    val createdAt: String,

    @SerialName("user")
    val user: UserData? = null
) {

    @Serializable
    data class UserData(
        @SerialName("id")
        val id: Int,

        @SerialName("name")
        val name: String,

        @SerialName("email")
        val email: String
    )
}