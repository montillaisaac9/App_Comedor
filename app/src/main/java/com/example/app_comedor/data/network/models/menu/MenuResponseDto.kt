package com.example.app_comedor.data.network.models.menu

import com.example.app_comedor.data.db.entity.DishEntity
import com.example.app_comedor.data.db.entity.MenuItemEntity
import com.example.app_comedor.data.db.entity.MenuItemWithDish

// --- DTOs ---
data class DishDTO(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val photo: String = "",
    val votesCount: Int = 0,
    val calories: Int = 0,
    val averageRating: Float = 0.0F,
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
    val menuItems: List<MenuItemDTO> = emptyList(),
)

data class MenuItemDTO(
    val id: Int = 0,
    val date: String = "",
    val weekDay: String = "",
    val dish: DishDTO? = null
)

// --- Funciones de extensión ---

fun Dish.toDto(): DishDTO = DishDTO(
    id           = this.id ?: 0,
    title        = this.title ?: "",
    description  = this.description ?: "",
    photo        = this.photo ?: "",
    votesCount   = this.votesCount ?: 0,
    averageRating = this.averageRating ?: 0.0f,
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
    menuItems  = this.menuItems?.map { it.toDto() } ?: emptyList(),
)

fun MenuItem.toDto(): MenuItemDTO = MenuItemDTO(
    id = this.id ?: 0,
    date = this.date ?: "",
    weekDay = this.weekDay ?: "",
    dish = this.dish?.toDto()
)
// --- Mapeo de DishDTO a DishEntity ---
fun DishDTO.toEntity(): DishEntity = DishEntity(
    id            = this.id,
    title         = this.title,
    description   = this.description,
    photo         = this.photo,
    votesCount    = this.votesCount,
    calories      = this.calories,
    cost          = this.cost,
    carbohydrates = this.carbohydrates,
    proteins      = this.proteins,
    fats          = this.fats,
    averageRating = this.averageRating
)

// --- Mapeo de MenuItemDTO a MenuItemEntity ---
fun MenuItemDTO.toEntity(): MenuItemEntity = MenuItemEntity(
    id      = this.id,
    date    = this.date,
    weekDay = this.weekDay,
    // Asumimos que dish?.id nunca será 0 para items válidos; ajusta según tu lógica de negocio
    dishId  = this.dish?.id ?: 0
)

// --- (Opcional) Mapeo de MenuItemDTO a MenuItemWithDish ---

fun DishEntity.toDto(): DishDTO = DishDTO(
    id            = this.id,
    title         = this.title,
    description   = this.description,
    photo         = this.photo,
    votesCount    = this.votesCount,
    calories      = this.calories,
    cost          = this.cost,
    averageRating = this.averageRating,
    carbohydrates = this.carbohydrates,
    proteins      = this.proteins,
    fats          = this.fats
)


fun MenuItemWithDish.relationToDto(): MenuItemDTO = MenuItemDTO(
    id      = this.menuItem.id,
    date    = this.menuItem.date,
    weekDay = this.menuItem.weekDay,
    dish     = this.dish.toDto()
)

