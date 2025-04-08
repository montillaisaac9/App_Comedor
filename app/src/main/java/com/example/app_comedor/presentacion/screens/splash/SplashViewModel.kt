package com.example.app_comedor.presentacion.screens.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_comedor.data.db.entity.UserEntity
import com.example.app_comedor.domain.usecase.UseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class SplashViewModel constructor(
    private val useCase: UseCase
): ViewModel() {

    var responseDataBase by mutableStateOf<UserEntity?>(null)
        private set

    fun verifyUser() = viewModelScope.launch {
        responseDataBase = useCase.auth.getLocaleUser().first()
    }
}
