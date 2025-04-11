package com.example.app_comedor.data.repository

import com.example.app_comedor.data.db.dao.UserDao
import com.example.app_comedor.data.network.client.ApiServiceImpl
import com.example.app_comedor.data.network.models.auth.ResponseCarriers
import com.example.app_comedor.data.network.models.menu.WeeklyMenuDTO
import com.example.app_comedor.data.network.response.ResponseBase
import com.example.app_comedor.domain.repository.RepositoryMenu
import com.example.app_comedor.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class RepositoryMenuImp(
    private val apiService: ApiServiceImpl,
): RepositoryMenu {


    override suspend fun getWeeklyMenu(): Flow<ApiResult<ResponseBase<WeeklyMenuDTO>?>> =
        apiService.get<WeeklyMenuDTO>(
            url = "menu/week",
        )


}