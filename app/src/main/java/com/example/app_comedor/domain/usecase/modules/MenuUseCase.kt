package com.example.app_comedor.domain.usecase.modules

import com.example.app_comedor.data.repository.RepositoryMenuImp

class MenuUseCase(val repositoryImpl: RepositoryMenuImp) {

    suspend fun getWeeklyMenu() = repositoryImpl.getWeeklyMenu()

}