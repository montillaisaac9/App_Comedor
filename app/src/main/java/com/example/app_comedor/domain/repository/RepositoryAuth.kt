package com.example.app_comedor.domain.repository

import com.example.app_comedor.data.db.entity.UserEntity
import com.example.app_comedor.data.network.models.auth.LoginParams
import com.example.app_comedor.data.network.models.auth.User
import com.example.app_comedor.data.network.response.ResponseBase
import com.example.app_comedor.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface RepositoryAuth {

    suspend fun logIn(params: LoginParams):Flow<ApiResult<ResponseBase<User>?>>

    fun getLocalPerfil(): Flow<UserEntity>
}