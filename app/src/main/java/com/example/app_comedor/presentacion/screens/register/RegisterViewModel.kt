package com.example.app_comedor.presentacion.screens.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.app_comedor.domain.usecase.UseCase
import com.example.app_comedor.presentacion.screens.register.components.DataRegister

class RegisterViewModel (
    private val useCase: UseCase
): ViewModel() {

    var state by mutableStateOf(DataRegister())
        private set

    fun setValue(key: String, value: Any) {
        state = when (key) {
            "email" -> state.copy(email = value as String)
            "password" -> state.copy(password = value as String)
            "confirmPassword" -> state.copy(confirmPassword = value as String)
            "identification" -> state.copy(identification = value as String)
            "name" -> state.copy(name = value as String)
            "securityWord" -> state.copy(securityWord = value as String)
            "careerIds" -> state.copy(careerIds = value as List<Int>)
            "errMessge" -> state.copy(errMessge = value as String)
            else -> state
        }
    }

}