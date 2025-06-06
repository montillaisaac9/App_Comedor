package com.example.app_comedor.domain.repository

import com.example.app_comedor.data.db.entity.DishEntity
import com.example.app_comedor.data.network.models.dish.CommentResponse
import com.example.app_comedor.data.network.models.dish.ScoreResponse
import com.example.app_comedor.data.network.models.dish.params.CreateComment
import com.example.app_comedor.data.network.models.dish.params.CreateScore
import com.example.app_comedor.data.network.models.dish.params.EditComment
import com.example.app_comedor.data.network.models.dish.params.EditScore
import com.example.app_comedor.data.network.models.dish.params.FindScoreAndComment
import com.example.app_comedor.data.network.response.ResponseBase
import com.example.app_comedor.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface RepositoryDish {

    suspend fun createDishScore(score: CreateScore):Flow<ApiResult<ResponseBase<String>?>>

    suspend fun createDishComment(newComment: CreateComment):Flow<ApiResult<ResponseBase<String>?>>

    suspend fun findScore(params: FindScoreAndComment): Flow<ApiResult<ResponseBase<ScoreResponse>?>>

    suspend fun findComment(params: FindScoreAndComment): Flow<ApiResult<ResponseBase<CommentResponse>?>>

    suspend fun editScore(score: EditScore, id: Int):Flow<ApiResult<ResponseBase<ScoreResponse>?>>

    suspend fun editComment(newComment: EditComment, id: Int):Flow<ApiResult<ResponseBase<CommentResponse>?>>

    fun getDishByIdLocal(id: Int): Flow<DishEntity?>
}