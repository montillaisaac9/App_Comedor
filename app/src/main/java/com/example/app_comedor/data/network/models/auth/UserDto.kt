package com.example.app_comedor.data.network.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val id: Int,
    val email: String,
    val name: String,
    val identification: String,
    val role: String,
    val securityWord: String,
    val isActive: Boolean,
    val photo: String,
    val careers: List<CareerDTO>
){
    @Serializable
    data class CareerDTO(
        val id: Int,
        val name: String
    ) {
        companion object
    }
}


