package com.example.app_comedor.presentacion.screens.menu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_comedor.data.network.models.menu.MenuItemDTO
import com.example.app_comedor.data.network.models.menu.WeeklyMenu
import com.example.app_comedor.data.network.models.menu.relationToDto
import com.example.app_comedor.data.network.models.menu.toEntity
import com.example.app_comedor.data.network.response.ResponseBase
import com.example.app_comedor.domain.usecase.UseCase
import com.example.app_comedor.utils.ApiResult
import kotlinx.coroutines.launch

class MenuViewModel(
    private val useCase: UseCase
): ViewModel() {

    var responseMenu by mutableStateOf<ApiResult<ResponseBase<WeeklyMenu>?>>(ApiResult.Loading())
        private set
    var localMenu by mutableStateOf<List<MenuItemDTO>>(emptyList())
        private set
    init {
        getMenu()
    }

    fun getMenu() = viewModelScope.launch {
        useCase.menu.getWeeklyMenu().collect {
            responseMenu = it
        }
    }

    fun saveLocalMenu(items: List<MenuItemDTO>) = viewModelScope.launch{
        items.forEach {
            useCase.menu.saveLocalMenu(it.toEntity())
            useCase.menu.saveLocalDish(it.dish!!.toEntity())
        }
    }

    fun getLocalMenu() = viewModelScope.launch {
        localMenu = useCase.menu.getLocalMenu().map { it.relationToDto() }
    }
}