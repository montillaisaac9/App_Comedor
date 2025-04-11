package com.example.app_comedor.domain.repository


import com.example.app_comedor.data.network.models.menu.WeeklyMenuDTO
import com.example.app_comedor.data.network.response.ResponseBase
import com.example.app_comedor.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface RepositoryMenu {

    suspend fun getWeeklyMenu():Flow<ApiResult<ResponseBase<WeeklyMenuDTO>?>>
}