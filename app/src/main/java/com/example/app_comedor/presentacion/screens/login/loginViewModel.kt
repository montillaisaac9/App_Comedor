package com.example.app_comedor.presentacion.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_comedor.data.network.models.auth.LoginParams
import com.example.app_comedor.data.network.models.auth.User
import com.example.app_comedor.data.network.response.ResponseBase
import com.example.app_comedor.domain.usecase.UseCase
import com.example.app_comedor.presentacion.screens.login.components.DataLogin
import com.example.app_comedor.utils.ApiResult
import kotlinx.coroutines.launch

class LoginViewModel (
    private val useCase: UseCase
): ViewModel() {

    var loginResponse by mutableStateOf<ApiResult<ResponseBase<User>?>?>(null)
        private set

    var state by mutableStateOf(DataLogin())
        private set

    fun setTextEmail(it: String) {
        state = state.copy(email = it)
    }

    fun setTextPassword(it: String) {
        state = state.copy(password = it)
    }

    fun login() = viewModelScope.launch {
        val params = LoginParams(
            email = state.email,
            password = state.password
        )
        useCase.auth.login(params).collect {
            loginResponse = it
        }
    }

}