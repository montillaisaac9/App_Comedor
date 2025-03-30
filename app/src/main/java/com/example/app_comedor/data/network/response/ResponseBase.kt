package com.example.app_comedor.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseBase<T>(
    val success: Boolean,
    val data: T?,
    val error: ServerError?
)