package com.example.app_comedor.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class ServerError(
    val statusCode: Int,
    val path: String,
    val message: String,
    val timestamp: String
)