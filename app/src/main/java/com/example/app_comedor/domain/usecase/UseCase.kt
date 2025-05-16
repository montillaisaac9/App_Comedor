package com.example.app_comedor.domain.usecase


import com.example.app_comedor.domain.usecase.modules.AuthUseCase
import com.example.app_comedor.domain.usecase.modules.DishUseCase
import com.example.app_comedor.domain.usecase.modules.MenuUseCase

data class UseCase(
    val auth: AuthUseCase,
    val menu: MenuUseCase,
    val dish: DishUseCase
)
