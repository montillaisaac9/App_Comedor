package com.example.app_comedor.domain.repository

import com.example.app_comedor.data.db.entity.DishEntity
import com.example.app_comedor.data.network.models.dish.CreateComment
import com.example.app_comedor.data.network.models.dish.CreateScore
import com.example.app_comedor.data.network.models.menu.WeeklyMenu
import com.example.app_comedor.data.network.response.ResponseBase
import com.example.app_comedor.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface RepositoryDish {

    suspend fun createDishScore(score: CreateScore):Flow<ApiResult<ResponseBase<String>?>>

    suspend fun createDishComment(newComment: CreateComment):Flow<ApiResult<ResponseBase<String>?>>

    fun getDishByIdLocal(id: Int): Flow<DishEntity?>
}