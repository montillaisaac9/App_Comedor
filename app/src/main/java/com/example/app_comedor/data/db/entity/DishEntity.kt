package com.example.app_comedor.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dishes")
data class DishEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val photo: String,
    val votesCount: Int,
    val averageRating: Float,
    val calories: Int,
    val cost: Double,
    val carbohydrates: Int,
    val proteins: Int,
    val fats: Int
)