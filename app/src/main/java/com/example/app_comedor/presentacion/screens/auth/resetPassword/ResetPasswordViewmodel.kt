package com.example.app_comedor.presentacion.screens.auth.resetPassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_comedor.data.network.models.auth.ResetPasswordRequest
import com.example.app_comedor.data.network.response.ResponseBase
import com.example.app_comedor.domain.usecase.UseCase
import com.example.app_comedor.presentacion.screens.auth.resetPassword.components.DataResetPassword
import com.example.app_comedor.utils.ApiResult
import kotlinx.coroutines.launch

class ResetPasswordViewmodel (
    private val useCase: UseCase
): ViewModel() {

    var state by mutableStateOf(DataResetPassword())
        private set

    var resetPasswordResponse by mutableStateOf<ApiResult<ResponseBase<String>?>?>(null)
        private set


    fun setValue(key: String, value: Any) {
        state = when (key) {
            "email" -> state.copy(email = value as String)
            "password" -> state.copy(password = value as String)
            "confirmPassword" -> state.copy(confirmPassword = value as String)
            "wordSecurity" -> state.copy(wordSecurity = value as String)
            "errMessge" -> state.copy(errMsg = value as String)
            else -> state
        }
    }

    fun resetPassword() = viewModelScope.launch {
        val params = ResetPasswordRequest(
            email = state.email,
            securityWord = state.wordSecurity,
            newPassword = state.password
        )
        useCase.auth.resetPassword(params).collect { result ->
            resetPasswordResponse = result
        }
    }



}
