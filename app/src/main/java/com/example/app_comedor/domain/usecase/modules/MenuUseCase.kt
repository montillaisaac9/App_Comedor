package com.example.app_comedor.domain.usecase.modules

import com.example.app_comedor.data.db.entity.DishEntity
import com.example.app_comedor.data.db.entity.MenuItemEntity
import com.example.app_comedor.data.repository.RepositoryMenuImp

class MenuUseCase(val repositoryImpl: RepositoryMenuImp) {

    suspend fun getWeeklyMenu() = repositoryImpl.getWeeklyMenu()

    suspend fun saveLocalMenu(menuItem: MenuItemEntity) = repositoryImpl.saveWeeklyMenu(menuItem)

    suspend fun saveLocalDish(dish: DishEntity) = repositoryImpl.saveDish(dish)

    suspend fun getLocalMenu() = repositoryImpl.getLocalDailyMenu()
}