package com.example.app_comedor.domain.usecase.modules


import com.example.app_comedor.data.network.models.dish.params.CreateComment
import com.example.app_comedor.data.network.models.dish.params.CreateScore
import com.example.app_comedor.data.network.models.dish.params.EditComment
import com.example.app_comedor.data.network.models.dish.params.EditScore
import com.example.app_comedor.data.network.models.dish.params.FindScoreAndComment
import com.example.app_comedor.domain.repository.RepositoryDish

class DishUseCase(val repositoryImpl: RepositoryDish) {

    fun getLocalDish(id: Int) = repositoryImpl.getDishByIdLocal(id)

    suspend fun createScore(newRating: CreateScore) = repositoryImpl.createDishScore(newRating)

    suspend fun createComment(newComment: CreateComment) = repositoryImpl.createDishComment(newComment)

    suspend fun findScore(params: FindScoreAndComment) = repositoryImpl.findScore(params)

    suspend fun findComment(params: FindScoreAndComment) = repositoryImpl.findComment(params)

    suspend fun editScore(id: Int, params: EditScore) = repositoryImpl.editScore(params, id)

    suspend fun editComment(id: Int, params: EditComment) = repositoryImpl.editComment(params, id)
}