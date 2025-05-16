package com.example.app_comedor.presentacion.screens.dish

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_comedor.data.network.models.menu.DishDTO
import com.example.app_comedor.data.network.models.menu.MenuItemDTO
import com.example.app_comedor.data.network.models.menu.toDto
import com.example.app_comedor.domain.usecase.UseCase
import kotlinx.coroutines.launch

class DishViewModel (
    private val useCase: UseCase
): ViewModel() {

    var dish by mutableStateOf<DishDTO?>(null)
        private set
        
    fun getLocalDish(id: Int) = viewModelScope.launch { 
        useCase.dish.getLocalDish(id).collect { 
            dish = it?.toDto()
        }
    }
    
}