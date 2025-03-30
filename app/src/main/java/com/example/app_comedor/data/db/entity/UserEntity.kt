package com.example.app_comedor.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.app_comedor.data.db.converter.CareerTypeConverter

@Entity(tableName = "users")
@TypeConverters(CareerTypeConverter::class)
data class UserEntity(
    @PrimaryKey val id: Int,
    val email: String,
    val name: String,
    val identification: String,
    val role: String,
    val securityWord: String,
    val isActive: Boolean,
    val photo: String,
    val careers: String
)