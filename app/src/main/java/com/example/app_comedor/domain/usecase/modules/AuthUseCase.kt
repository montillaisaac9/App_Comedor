package com.example.app_comedor.domain.usecase.modules

import com.example.app_comedor.data.db.entity.UserEntity
import com.example.app_comedor.data.network.models.auth.CreateUser
import com.example.app_comedor.data.network.models.auth.LoginParams
import com.example.app_comedor.data.network.models.auth.ResetPasswordRequest
import com.example.app_comedor.data.repository.RepositoryAuthImp
import java.io.File

class AuthUseCase constructor(val repositoryImpl: RepositoryAuthImp){

    suspend fun getCarrier() = repositoryImpl.carriers()

    suspend fun login(params: LoginParams) = repositoryImpl.logIn(params)

    suspend fun register(user: CreateUser, imageFile: File?) = repositoryImpl.registerUser(user, imageFile)

    suspend fun resetPassword(params: ResetPasswordRequest) = repositoryImpl.resetPassword(params)

    fun getLocaleUser() = repositoryImpl.getLocalProfile()

    suspend fun saveLocalUser(user: UserEntity) = repositoryImpl.saveLocal(user)

    suspend fun deleteLocalUser() = repositoryImpl.deleteLocalUser()
}