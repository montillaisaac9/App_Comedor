package com.example.app_comedor.data.repository

import com.example.app_comedor.data.db.dao.DishDao
import com.example.app_comedor.data.db.dao.MenuItemDao
import com.example.app_comedor.data.db.entity.DishEntity
import com.example.app_comedor.data.db.entity.MenuItemEntity
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
    private val menuDao: MenuItemDao,
    private val dishDao: DishDao
): RepositoryMenu {


    override suspend fun getWeeklyMenu(): Flow<ApiResult<ResponseBase<WeeklyMenu>?>> =
        apiService.get<WeeklyMenu>(
            url = "menu/week",
        )

    override suspend fun saveWeeklyMenu(weeklyMenu: MenuItemEntity) {
        menuDao.insertMenuItem(weeklyMenu)
    }

    override suspend fun saveDish(dish: DishEntity) {
        dishDao.insertDish(dish)
    }

    override suspend fun getLocalDailyMenu() = menuDao.getAllMenuItems()


}