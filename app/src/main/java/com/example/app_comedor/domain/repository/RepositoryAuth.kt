package com.example.app_comedor.domain.repository

import com.example.app_comedor.data.db.entity.UserEntity
import com.example.app_comedor.data.network.models.auth.CreateUser
import com.example.app_comedor.data.network.models.auth.LoginParams
import com.example.app_comedor.data.network.models.auth.ResponseCarriers
import com.example.app_comedor.data.network.models.auth.User
import com.example.app_comedor.data.network.response.ResponseBase
import com.example.app_comedor.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface RepositoryAuth {

    suspend fun carriers():Flow<ApiResult<ResponseBase<List<ResponseCarriers>>?>>

    suspend fun logIn(params: LoginParams):Flow<ApiResult<ResponseBase<User>?>>

    suspend fun registerUser(user: CreateUser, imageFile: File? = null): Flow<ApiResult<ResponseBase<String>?>>

    fun getLocalPerfil(): Flow<UserEntity>
}