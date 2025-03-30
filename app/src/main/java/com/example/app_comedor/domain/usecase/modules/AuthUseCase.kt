package com.example.app_comedor.domain.usecase.modules

import com.example.app_comedor.data.network.models.auth.LoginParams
import com.example.app_comedor.data.repository.RepositoryAuthImp

class AuthUseCase constructor(val repositoryImpl: RepositoryAuthImp){

    suspend fun login(params: LoginParams) = repositoryImpl.logIn(params)
}