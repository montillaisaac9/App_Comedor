package com.example.app_comedor.data.network.models.menu

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeeklyMenuDTO(
    @SerialName("id")
    val id: Int?,
    @SerialName("weekStart")
    val weekStart: String?,
    @SerialName("weekEnd")
    val weekEnd: String?,
    @SerialName("isActive")
    val isActive: Boolean?,
    @SerialName("createdAt")
    val createdAt: String?,
    @SerialName("updatedAt")
    val updatedAt: String?,
    @SerialName("mondayId")
    val mondayId: Int?,
    @SerialName("tuesdayId")
    val tuesdayId: Int?,
    @SerialName("wednesdayId")
    val wednesdayId: Int?,
    @SerialName("thursdayId")
    val thursdayId: Int?,
    @SerialName("fridayId")
    val fridayId: Int?,
    @SerialName("monday")
    val monday: DishDTO?,
    @SerialName("tuesday")
    val tuesday: DishDTO?,
    @SerialName("wednesday")
    val wednesday: DishDTO?,
    @SerialName("thursday")
    val thursday: DishDTO?,
    @SerialName("friday")
    val friday: DishDTO?
)

@Serializable
data class DishDTO(
    @SerialName("id")
    val id: Int?,
    @SerialName("title")
    val title: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("photo")
    val photo: String?,
    @SerialName("votesCount")
    val votesCount: Int?,
    @SerialName("calories")
    val calories: Int?,
    @SerialName("cost")
    val cost: Double?,
    @SerialName("carbohydrates")
    val carbohydrates: Int?,
    @SerialName("proteins")
    val proteins: Int?,
    @SerialName("fats")
    val fats: Int?
)
