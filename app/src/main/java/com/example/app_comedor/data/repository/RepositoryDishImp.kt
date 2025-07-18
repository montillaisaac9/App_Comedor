package com.example.app_comedor.data.repository

import com.example.app_comedor.data.db.dao.DishDao
import com.example.app_comedor.data.db.dao.MenuItemDao
import com.example.app_comedor.data.db.entity.DishEntity
import com.example.app_comedor.data.db.entity.MenuItemWithDish
import com.example.app_comedor.data.network.client.ApiServiceImpl
import com.example.app_comedor.data.network.models.dish.AttendanceResponse
import com.example.app_comedor.data.network.models.dish.CommentResponse
import com.example.app_comedor.data.network.models.dish.ScoreResponse
import com.example.app_comedor.data.network.models.dish.params.CreateAttendance
import com.example.app_comedor.data.network.models.dish.params.CreateComment
import com.example.app_comedor.data.network.models.dish.params.CreateScore
import com.example.app_comedor.data.network.models.dish.params.EditComment
import com.example.app_comedor.data.network.models.dish.params.EditScore
import com.example.app_comedor.data.network.models.dish.params.FindScoreAndComment
import com.example.app_comedor.data.network.response.ResponseBase
import com.example.app_comedor.domain.repository.RepositoryDish
import com.example.app_comedor.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

class RepositoryDishImp(
    private val apiService: ApiServiceImpl,
    private val dishDao: DishDao,
    private val menuItemsDao: MenuItemDao
): RepositoryDish {


    override suspend fun createDishScore(score: CreateScore): Flow<ApiResult<ResponseBase<String>?>> =
        apiService.post<String>(
            url = "dish-ratting",
            bodyJson = Json.encodeToJsonElement(score)
        )

    override suspend fun createDishComment(newComment: CreateComment): Flow<ApiResult<ResponseBase<String>?>> =
        apiService.post<String>(
            url = "comment",
            bodyJson = Json.encodeToJsonElement(newComment)
        )

    override suspend fun findScore(params: FindScoreAndComment): Flow<ApiResult<ResponseBase<ScoreResponse>?>> =
        apiService.post<ScoreResponse>(
            url = "dish-ratting/userDish",
            bodyJson = Json.encodeToJsonElement(params)
        )

    override suspend fun findComment(params: FindScoreAndComment): Flow<ApiResult<ResponseBase<CommentResponse>?>> =
        apiService.post<CommentResponse>(
            url = "comment/userDish",
            bodyJson = Json.encodeToJsonElement(params)
        )

    override suspend fun editScore(score: EditScore, id: Int): Flow<ApiResult<ResponseBase<ScoreResponse>?>> =
        apiService.patch<ScoreResponse>(
            url = "dish-ratting/$id",
            bodyJson = Json.encodeToJsonElement(score)
        )


    override suspend fun editComment(newComment: EditComment, id: Int): Flow<ApiResult<ResponseBase<CommentResponse>?>> =
        apiService.patch<CommentResponse>(
            url = "comment/$id",
            bodyJson = Json.encodeToJsonElement(newComment)
        )

    override suspend fun createAttendance(attendance: CreateAttendance) : Flow<ApiResult<ResponseBase<String>?>> =
        apiService.post<String>(
            url = "attendance",
            bodyJson = Json.encodeToJsonElement(attendance)
        )

    override suspend fun verifyAttendance(
        userId: Int,
        itemId: Int
    ): Flow<ApiResult<ResponseBase<AttendanceResponse>?>> =
        apiService.post<AttendanceResponse>(
            url = "attendance/user/$userId/menu-item/$itemId",
            bodyJson = Json.encodeToJsonElement(Unit)
        )

    override fun getDishByIdLocal(id: Int): Flow<DishEntity?> = dishDao.getDishById(id)
    override fun getMenuItemsLocal(id: Int): Flow<MenuItemWithDish?> = menuItemsDao.getMenuItemById(id)
}