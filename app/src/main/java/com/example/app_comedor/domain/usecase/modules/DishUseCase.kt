package com.example.app_comedor.domain.usecase.modules


import com.example.app_comedor.domain.repository.RepositoryDish

class DishUseCase(val repositoryImpl: RepositoryDish) {

    fun getLocalDish(id: Int) = repositoryImpl.getDishByIdLocal(id)
}