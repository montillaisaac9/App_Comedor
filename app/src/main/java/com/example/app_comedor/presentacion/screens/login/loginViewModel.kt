package com.example.app_comedor.presentacion.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.app_comedor.domain.usecase.UseCase
import com.example.app_comedor.presentacion.screens.login.components.Data

class LoginViewModel constructor(
    private val useCase: UseCase
): ViewModel() {

    var state by mutableStateOf(Data())
        private set

    fun setTextEmail(it: String) {
        state = state.copy(email = it)
    }

    fun setTextPassword(it: String) {
        state = state.copy(password = it)
    }

}