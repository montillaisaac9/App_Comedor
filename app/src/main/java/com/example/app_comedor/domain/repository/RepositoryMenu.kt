package com.example.app_comedor.domain.repository


import com.example.app_comedor.data.db.entity.DishEntity
import com.example.app_comedor.data.db.entity.MenuItemEntity
import com.example.app_comedor.data.db.entity.MenuItemWithDish
import com.example.app_comedor.data.network.models.menu.WeeklyMenu
import com.example.app_comedor.data.network.response.ResponseBase
import com.example.app_comedor.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface RepositoryMenu {

    suspend fun getWeeklyMenu():Flow<ApiResult<ResponseBase<WeeklyMenu>?>>

    suspend fun saveWeeklyMenu(weeklyMenu: MenuItemEntity)

    suspend fun saveDish(dish: DishEntity)

    suspend fun getLocalDailyMenu() : List<MenuItemWithDish>
}