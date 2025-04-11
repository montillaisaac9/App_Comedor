package com.example.app_comedor.presentacion.screens.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_comedor.data.network.models.auth.CreateUser
import com.example.app_comedor.data.network.models.auth.ResponseCarriers
import com.example.app_comedor.data.network.models.auth.User
import com.example.app_comedor.data.network.models.auth.toSpinnerItem
import com.example.app_comedor.data.network.response.ResponseBase
import com.example.app_comedor.domain.usecase.UseCase
import com.example.app_comedor.presentacion.common.spinner.SpinnerItem
import com.example.app_comedor.presentacion.screens.auth.register.components.DataRegister
import com.example.app_comedor.utils.ApiResult
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File

class RegisterViewModel (
    private val useCase: UseCase
): ViewModel() {

    var state by mutableStateOf(DataRegister())
        private set

    var listCarriers by mutableStateOf<List<SpinnerItem>>(emptyList())

    var carrierRespose by mutableStateOf<ApiResult<ResponseBase<List<ResponseCarriers>>?>>(ApiResult.Loading())
        private set

    var registerResponse by mutableStateOf<ApiResult<ResponseBase<String>?>?>(null)
        private set

    fun getCarriers() = viewModelScope.launch {
        useCase.auth.getCarrier().collect { result ->
            Timber.e("PETICION ${result.data}")
            when(result) {
                is ApiResult.Error -> { /* Manejar error */ }
                is ApiResult.Loading -> { /* Opcionalmente mostrar loading */ }
                is ApiResult.Success -> {
                    val a = result.data?.data?.map { it.toSpinnerItem() } ?: emptyList()
                    listCarriers = a
                    carrierRespose = result
                }
            }
        }
    }

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
            "numberCarriers" -> {
                // Por seguridad, limitamos también en el setValue
                val number = when (value) {
                    is Int -> value
                    is String -> value.toIntOrNull() ?: 0
                    else -> 0
                }
                state.copy(numberCarriers = number.coerceIn(0, 3))
            }
            else -> state
        }
    }

    fun setImage(image: File?){
        state = state.copy(image = image)
    }


    // Función para actualizar la selección de carrera (por índice) con el id de la carrera seleccionada.
    fun updateCareerSelection(index: Int, careerId: Int) {
        // Se crea una lista mutable a partir de la lista actual de ids
        val updatedList = state.careerIds.toMutableList()
        // Asegurarse de que la lista tenga suficiente tamaño
        while (updatedList.size <= index) {
            updatedList.add(0)
        }
        // Se actualiza el valor en el índice correspondiente
        updatedList[index] = careerId
        // Se actualiza el dataRegister con la nueva lista de ids
        state = state.copy(careerIds = updatedList)
    }

    // Función para eliminar la última carrera seleccionada
    fun removeLastCareer() {
        if (state.numberCarriers > 1) {
            val newNumberCarriers = state.numberCarriers - 1
            val updatedCareerIds = state.careerIds.take(newNumberCarriers)
            state = state.copy(
                numberCarriers = newNumberCarriers,
                careerIds = updatedCareerIds
            )
        }
    }

    // Función auxiliar para obtener el SpinnerItem seleccionado a partir del id almacenado.
    fun getSelectedSpinner(index: Int): SpinnerItem? {
        return state.careerIds.getOrNull(index)?.let { careerId ->
            // Se busca en la lista de opciones el ítem cuyo value (convertido a Int) coincida con el id
            listCarriers.find { it.id.toIntOrNull() == careerId }
        }
    }

    fun clearForm() {
        setValue("name", "")
        setValue("email", "")
        setValue("securityWord", "")
        setValue("password", "")
        setValue("confirmPassword", "")
        setValue("identification", "")
        setValue("numberCarriers", 0)
        setValue("careerIds",emptyList<Int>())
    }

    fun register() = viewModelScope.launch {
        val params = CreateUser(
            email = state.email,
            password = state.password,
            name = state.name,
            isActive = true,
            securityWord = state.securityWord,
            identification = state.identification,
            careerIds = state.careerIds
        )
        useCase.auth.register(params, state.image).collect {
           registerResponse = it
        }
    }

}