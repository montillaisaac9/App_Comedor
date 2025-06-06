package com.example.app_comedor.data.network.models.dish.params

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateComment (
    @SerialName("userId")
    val userId: Int,

    @SerialName("dishId")
    val dishId: Int,

    @SerialName("text")
    val text: String,

)