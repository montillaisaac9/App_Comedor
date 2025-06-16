package com.example.app_comedor.data.network.models.dish

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttendanceResponse(
    @SerialName("id")
    val id: Int,

    @SerialName("createdAt")
    val createdAt: String,

    @SerialName("user")
    val user: User,

    @SerialName("menuItem")
    val menuItem: MenuItem
)

@Serializable
data class User(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("identification")
    val identification: String
)

@Serializable
data class MenuItem(
    @SerialName("id")
    val id: Int,

    @SerialName("date")
    val date: String,

    @SerialName("weekDay")
    val weekDay: String,

    @SerialName("dish")
    val dish: Dish
)

@Serializable
data class Dish(
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String
)

