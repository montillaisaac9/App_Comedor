package com.example.app_comedor.data.repository

import com.example.app_comedor.data.db.dao.DishDao
import com.example.app_comedor.data.db.dao.MenuItemDao
import com.example.app_comedor.data.db.entity.DishEntity
import com.example.app_comedor.data.network.client.ApiServiceImpl
import com.example.app_comedor.domain.repository.RepositoryDish
import kotlinx.coroutines.flow.Flow

class RepositoryDishImp(
    private val apiService: ApiServiceImpl,

    private val dishDao: DishDao
): RepositoryDish {

    override fun getDishByIdLocal(id: Int): Flow<DishEntity?> = dishDao.getDishById(id)
}