package com.example.app_comedor.data.repository

import com.example.app_comedor.data.db.dao.DishDao
import com.example.app_comedor.data.db.dao.MenuItemDao
import com.example.app_comedor.data.db.entity.DishEntity
import com.example.app_comedor.data.network.client.ApiServiceImpl
import com.example.app_comedor.data.network.models.dish.CreateComment
import com.example.app_comedor.data.network.models.dish.CreateScore
import com.example.app_comedor.data.network.response.ResponseBase
import com.example.app_comedor.domain.repository.RepositoryDish
import com.example.app_comedor.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class RepositoryDishImp(
    private val apiService: ApiServiceImpl,
    private val dishDao: DishDao
): RepositoryDish {


    override suspend fun createDishScore(score: CreateScore): Flow<ApiResult<ResponseBase<String>?>> {
        TODO("Not yet implemented")
    }

    override suspend fun createDishComment(newComment: CreateComment): Flow<ApiResult<ResponseBase<String>?>> {
        TODO("Not yet implemented")
    }


    override fun getDishByIdLocal(id: Int): Flow<DishEntity?> = dishDao.getDishById(id)
}