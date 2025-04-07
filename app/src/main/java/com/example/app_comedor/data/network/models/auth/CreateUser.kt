package com.example.app_comedor.data.network.models.auth
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Role {
    STUDENT,
    ADMIN,
    EMPLOYEE
}

@Serializable
data class CreateUser(
    @SerialName("email")
    val email: String,

    @SerialName("identification")
    val identification: String,

    @SerialName("name")
    val name: String,

    @SerialName("password")
    val password: String,

    @SerialName("securityWord")
    val securityWord: String,

    @SerialName("role")
    val role: Role = Role.STUDENT, // Usamos el enum definido

    @SerialName("position")
    val position: String? = null,

    @SerialName("photo")
    val photo: String? = null,

    @SerialName("isActive")
    val isActive: Boolean? = null,

    @SerialName("careerIds")
    val careerIds: List<Int>
)
