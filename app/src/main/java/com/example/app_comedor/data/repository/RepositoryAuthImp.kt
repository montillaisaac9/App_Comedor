package com.example.app_comedor.data.repository

import com.example.app_comedor.data.db.dao.UserDao
import com.example.app_comedor.data.db.entity.UserEntity
import com.example.app_comedor.data.network.client.ApiServiceImpl
import com.example.app_comedor.data.network.models.auth.CreateUser
import com.example.app_comedor.data.network.models.auth.LoginParams
import com.example.app_comedor.data.network.models.auth.ResponseCarriers
import com.example.app_comedor.data.network.models.auth.User
import com.example.app_comedor.data.network.response.ResponseBase
import com.example.app_comedor.domain.repository.RepositoryAuth
import com.example.app_comedor.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import java.io.File

class RepositoryAuthImp(
    private val userDao: UserDao,
    private val apiService: ApiServiceImpl,
): RepositoryAuth {
    override suspend fun carriers(): Flow<ApiResult<ResponseBase<List<ResponseCarriers>>?>> =
        apiService.get<List<ResponseCarriers>>(
            url = "carriers/active",
        )


    override suspend fun logIn(params: LoginParams): Flow<ApiResult<ResponseBase<User>?>> =
        apiService.post<User>(
            url = "authentication/login",
            bodyJson = Json.encodeToJsonElement(params)
        )

    override suspend fun registerUser(
        user: CreateUser,
        imageFile: File?
    ): Flow<ApiResult<ResponseBase<String>?>> =
        apiService.postMultipart(
            url = "authentication/register",
            user = user,
            imageFile = imageFile
        )

    override fun getLocalPerfil(): Flow<UserEntity> = userDao.getUserFlow()
}
