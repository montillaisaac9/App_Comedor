package com.example.app_comedor.presentacion.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_comedor.data.db.entity.UserEntity
import com.example.app_comedor.domain.usecase.UseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val useCase: UseCase
): ViewModel() {

    var responseDataBase by mutableStateOf<UserEntity>(UserEntity())
        private set

    init {
        getUser()
    }

    fun getUser() = viewModelScope.launch {
        useCase.auth.getLocaleUser().collect {
            responseDataBase = it
        }
    }

    fun closeSession() = viewModelScope.launch {
        useCase.auth.deleteLocalUser()
    }
}