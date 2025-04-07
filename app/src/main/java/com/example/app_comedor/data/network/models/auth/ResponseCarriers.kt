package com.example.app_comedor.data.network.models.auth

import com.example.app_comedor.presentacion.common.spinner.SpinnerItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseCarriers(
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?
)

fun ResponseCarriers.toSpinnerItem() = SpinnerItem(
    id = this.id?: "",
    label = this.name?: ""
)
