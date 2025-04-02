package com.example.app_comedor.data.network.models.auth

import com.example.app_comedor.data.network.models.auth.UserDTO.CareerDTO
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
    )
}

fun User.toDto(): UserDTO {
    return UserDTO(
        id = this.id ?: 0,
        email = this.email ?: "",
        name = this.name ?: "",
        identification = this.identification ?: "",
        role = this.role ?: "",
        securityWord = this.securityWord ?: "",
        isActive = this.isActive ?: false,
        photo = this.photo ?: "",
        careers = this.careers?.map { CareerDTO(it.id ?: 0, it.name ?: "") } ?: emptyList()
    )
}





