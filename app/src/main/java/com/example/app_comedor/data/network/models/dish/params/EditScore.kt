package com.example.app_comedor.data.network.models.dish.params

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditScore (

    @SerialName("rating")
    val rating: Float,

    )