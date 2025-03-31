package com.example.app_comedor.presentacion.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_comedor.data.db.entity.UserEntity
import com.example.app_comedor.domain.usecase.UseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber


class SplashViewModel constructor(
    private val useCase: UseCase
): ViewModel() {

    var perfil: UserEntity? = null

    fun verifyPerfil() = viewModelScope.launch {
        perfil = useCase.auth.getLocalePerfil().first()
    }
}
