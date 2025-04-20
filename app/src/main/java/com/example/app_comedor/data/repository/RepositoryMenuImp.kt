package com.example.app_comedor.data.repository

import com.example.app_comedor.data.network.client.ApiServiceImpl
import com.example.app_comedor.data.network.models.menu.WeeklyMenu
import com.example.app_comedor.data.network.response.ResponseBase
import com.example.app_comedor.domain.repository.RepositoryMenu
import com.example.app_comedor.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

class RepositoryMenuImp(
    private val apiService: ApiServiceImpl,
): RepositoryMenu {


    override suspend fun getWeeklyMenu(): Flow<ApiResult<ResponseBase<WeeklyMenu>?>> =
        apiService.get<WeeklyMenu>(
            url = "menu/week",
        )


}