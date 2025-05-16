package com.example.app_comedor.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "menu_items")
data class MenuItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val weekDay: String,
    val dishId: Int
)

data class MenuItemWithDish(
    @Embedded val menuItem: MenuItemEntity,
    @Relation(
        parentColumn = "dishId",
        entityColumn = "id"
    )
    val dish: DishEntity
)