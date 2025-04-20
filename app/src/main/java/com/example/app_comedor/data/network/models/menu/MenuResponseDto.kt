package com.example.app_comedor.data.network.models.menu

// --- DTOs ---

data class DishDTO(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val photo: String = "",
    val votesCount: Int = 0,
    val calories: Int = 0,
    val cost: Double = 0.0,
    val carbohydrates: Int = 0,
    val proteins: Int = 0,
    val fats: Int = 0
)

data class WeeklyMenuDTO(
    val id: Int = 0,
    val weekStart: String = "",
    val weekEnd: String = "",
    val isActive: Boolean = false,
    val createdAt: String = "",
    val updatedAt: String = "",
    val mondayId: Int = 0,
    val tuesdayId: Int = 0,
    val wednesdayId: Int = 0,
    val thursdayId: Int = 0,
    val fridayId: Int = 0,
    val monday: DishDTO? = null,
    val tuesday: DishDTO? = null,
    val wednesday: DishDTO? = null,
    val thursday: DishDTO? = null,
    val friday: DishDTO? = null
)


// --- Funciones de extensión ---

fun Dish.toDto(): DishDTO = DishDTO(
    id           = this.id ?: 0,
    title        = this.title ?: "",
    description  = this.description ?: "",
    photo        = this.photo ?: "",
    votesCount   = this.votesCount ?: 0,
    calories     = this.calories ?: 0,
    cost         = this.cost ?: 0.0,
    carbohydrates= this.carbohydrates ?: 0,
    proteins     = this.proteins ?: 0,
    fats         = this.fats ?: 0
)

fun WeeklyMenu.toDto(): WeeklyMenuDTO = WeeklyMenuDTO(
    id         = this.id ?: 0,
    weekStart  = this.weekStart ?: "",
    weekEnd    = this.weekEnd ?: "",
    isActive   = this.isActive ?: false,
    createdAt  = this.createdAt ?: "",
    updatedAt  = this.updatedAt ?: "",
    mondayId   = this.mondayId ?: 0,
    tuesdayId  = this.tuesdayId ?: 0,
    wednesdayId= this.wednesdayId ?: 0,
    thursdayId = this.thursdayId ?: 0,
    fridayId   = this.fridayId ?: 0,
    monday     = this.monday?.toDto(),
    tuesday    = this.tuesday?.toDto(),
    wednesday  = this.wednesday?.toDto(),
    thursday   = this.thursday?.toDto(),
    friday     = this.friday?.toDto()
)

