package com.example.app_comedor.data.network.models.dish

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateScore (
    @SerialName("userId")
    val userId: Int,

    @SerialName("dishId")
    val dishId: Int,

    @SerialName("rating")
    val rating: Int,

)