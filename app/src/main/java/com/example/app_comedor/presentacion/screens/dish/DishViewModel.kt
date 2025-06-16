package com.example.app_comedor.presentacion.screens.dish

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_comedor.data.network.models.dish.CommentResponse
import com.example.app_comedor.data.network.models.dish.ScoreResponse
import com.example.app_comedor.data.network.models.dish.params.CreateAttendance
import com.example.app_comedor.data.network.models.dish.params.CreateComment
import com.example.app_comedor.data.network.models.dish.params.CreateScore
import com.example.app_comedor.data.network.models.dish.params.EditComment
import com.example.app_comedor.data.network.models.dish.params.EditScore
import com.example.app_comedor.data.network.models.dish.params.FindScoreAndComment
import com.example.app_comedor.data.network.models.menu.DishDTO
import com.example.app_comedor.data.network.models.menu.toDto
import com.example.app_comedor.data.network.response.ResponseBase
import com.example.app_comedor.domain.usecase.UseCase
import com.example.app_comedor.utils.ApiResult
import com.example.app_comedor.utils.ApiResult.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber

class DishViewModel(
    private val useCase: UseCase
) : ViewModel() {

    var dish by mutableStateOf<DishDTO?>(null)
        private set

    var responseScoreCreateAttendaceComment by mutableStateOf<ApiResult<ResponseBase<String>?>?>(null)
    var responseCreateAttendance by mutableStateOf<ApiResult<ResponseBase<String>?>?>(null)
    var oldScore by mutableStateOf<ScoreResponse?>(null)
    var oldComment by mutableStateOf<CommentResponse?>(null)
    var isAttendance by mutableStateOf(false)

    fun getLocalDish(id: Int) = viewModelScope.launch {
        useCase.dish.getLocalDish(id).collect {
            dish = it?.toDto()
        }
    }

    fun findScoreAttendaceComment(menuId: Int) = viewModelScope.launch {
        val find = FindScoreAndComment(
            dishId = dish?.id ?: 1,
            userId = useCase.auth.getLocaleUser().first().id
        )
        useCase.dish.findScore(find).collect { result ->
            when (result) {
                is ApiResult.Error -> {
                    Timber.e(result.error.toString())
                    responseScoreCreateAttendaceComment = null
                }

                is ApiResult.Loading -> {
                    responseScoreCreateAttendaceComment = ApiResult.Loading()
                }

                is ApiResult.Success -> {
                    Timber.d("Success Score and Comment ${result.data?.data}")
                    responseScoreCreateAttendaceComment = null
                    oldScore = result.data?.data
                }
            }
        }
        useCase.dish.findComment(find).collect { result ->
            when (result) {
                is ApiResult.Error -> {
                    Timber.e(result.error.toString())
                    responseScoreCreateAttendaceComment = null
                }

                is ApiResult.Loading -> {
                    Timber.d("Loading Score and Comment")
                    responseScoreCreateAttendaceComment = ApiResult.Loading()
                }

                is ApiResult.Success -> {
                    Timber.d("Success Score and Comment ${result.data?.data}")
                    responseScoreCreateAttendaceComment = null
                    oldComment = result.data?.data
                }
            }
        }


        useCase.dish.findAttendance(
            userId = useCase.auth.getLocaleUser().first().id,
            itemId = useCase.dish.getLocalItemMenu(menuId).first()?.menuItem?.id?: 0
        ).collect { result ->
            when (result) {
                is ApiResult.Error -> {
                    Timber.e(result.error.toString())
                    responseScoreCreateAttendaceComment = null
                }

                is ApiResult.Loading -> {
                    Timber.d("Loading Score and Comment")
                    responseScoreCreateAttendaceComment = Loading()
                }

                is ApiResult.Success -> {
                    Timber.d("Success Score and Comment ${result.data?.data}")
                    responseScoreCreateAttendaceComment = null
                    if (result.data?.data != null) isAttendance = true
                }
            }
        }
    }

    fun submitRating(rating: Float, comment: String) {
        if (oldScore != null && oldComment != null) {
            editScoreAndComment(rating, comment)
        } else {
            sendScoreAndComment(rating, comment)
        }
    }

    fun sendScoreAndComment(rating: Float, comment: String) = viewModelScope.launch {
        val score = CreateScore(
            userId = useCase.auth.getLocaleUser().first().id,
            dishId = dish?.id ?: 1,
            rating = rating,
        )
        val comment = CreateComment(
            userId = useCase.auth.getLocaleUser().first().id,
            dishId = dish?.id ?: 1,
            text = comment,
        )
        var resScore: String? = null
        var resComment: String? = null
        useCase.dish.createScore(score).collect {
            when (it) {
                is ApiResult.Error -> {
                    responseScoreCreateAttendaceComment = ApiResult.Error(it.error.toString())
                    return@collect
                    Timber.e(it.error.toString())
                }

                is ApiResult.Loading -> {
                    Timber.e("Loading Score")
                    ApiResult.Loading<String>()
                }

                is ApiResult.Success -> {
                    Timber.e("Success Score ${it.data?.data}")
                    resScore = it.data?.data
                }
            }
        }
        useCase.dish.createComment(comment).collect {
            when (it) {
                is ApiResult.Error -> {
                    Timber.e(it.error.toString())
                    responseScoreCreateAttendaceComment = ApiResult.Error(it.error.toString())
                    return@collect
                }

                is ApiResult.Loading -> {
                    Timber.e("Loading Comment")
                    ApiResult.Loading<String>()
                }

                is ApiResult.Success -> {
                    Timber.e("Success Comment ${it.data?.data}")
                    resComment = it.data?.data
                }
            }
        }
        if (resScore != null && resComment != null) responseScoreCreateAttendaceComment =
            ApiResult.Success(
                ResponseBase(
                    data = "El comentario y la puntuación se han guardado correctamente",
                    error = null,
                    success = true,
                )
            )
        else if (resScore != null) responseScoreCreateAttendaceComment =
            ApiResult.Success(
                ResponseBase(
                    data = "La puntuación se han guardado correctamente",
                    error = null,
                    success = true,
                )
            )
        else if (resComment != null) responseScoreCreateAttendaceComment =
            ApiResult.Success(
                ResponseBase(
                    data = "El comentario se han guardado correctamente",
                    error = null,
                    success = true,
                )
            )
        else responseScoreCreateAttendaceComment = null
    }

    fun editScoreAndComment(rating: Float, comment: String) = viewModelScope.launch {
        val score = EditScore(
            rating = rating,
        )
        val comment = EditComment(
            text = comment,
        )
        var resScore: ScoreResponse? = null
        var resComment: CommentResponse? = null
        useCase.dish.editScore(oldScore?.id ?: 0, score).collect {
            when (it) {
                is ApiResult.Error -> {
                    responseScoreCreateAttendaceComment = ApiResult.Error(it.error.toString())
                    return@collect
                    Timber.e(it.error.toString())
                }

                is ApiResult.Loading -> {
                    Timber.e("Loading Score")
                    ApiResult.Loading<String>()
                }

                is ApiResult.Success -> {
                    Timber.e("Success Score ${it.data?.data}")
                    resScore = it.data?.data
                }
            }
        }
        useCase.dish.editComment(oldComment?.id ?: 0, comment).collect {
            when (it) {
                is ApiResult.Error -> {
                    Timber.e(it.error.toString())
                    responseScoreCreateAttendaceComment = ApiResult.Error(it.error.toString())
                    return@collect
                }

                is ApiResult.Loading -> {
                    Timber.e("Loading Comment")
                    ApiResult.Loading<String>()
                }

                is ApiResult.Success -> {
                    Timber.e("Success Comment ${it.data?.data}")
                    resComment = it.data?.data
                }
            }
        }
        if (resScore != null && resComment != null) responseScoreCreateAttendaceComment =
            ApiResult.Success(
                ResponseBase(
                    data = "El comentario y la puntuación se han guardado correctamente",
                    error = null,
                    success = true,
                )
            )
        else if (resScore != null) responseScoreCreateAttendaceComment =
            ApiResult.Success(
                ResponseBase(
                    data = "La puntuación se han guardado correctamente",
                    error = null,
                    success = true,
                )
            )
        else if (resComment != null) responseScoreCreateAttendaceComment =
            ApiResult.Success(
                ResponseBase(
                    data = "El comentario se han guardado correctamente",
                    error = null,
                    success = true,
                )
            )
        else responseScoreCreateAttendaceComment = null
        findScoreAttendaceComment(0)
    }

    fun createAttendance(menuId: Int) = viewModelScope.launch {
        val attendance = CreateAttendance(
            userId = useCase.auth.getLocaleUser().first().id,
            menuItemId = useCase.dish.getLocalItemMenu(menuId).first()?.menuItem?.id?: 0
        )

        useCase.dish.createAttendance(attendance).collect {
            responseCreateAttendance = it
        }
    }
}