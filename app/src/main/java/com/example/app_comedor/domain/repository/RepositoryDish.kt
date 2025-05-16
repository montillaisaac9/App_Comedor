package com.example.app_comedor.domain.repository

import com.example.app_comedor.data.db.entity.DishEntity
import kotlinx.coroutines.flow.Flow

interface RepositoryDish {

    fun getDishByIdLocal(id: Int): Flow<DishEntity?>
}