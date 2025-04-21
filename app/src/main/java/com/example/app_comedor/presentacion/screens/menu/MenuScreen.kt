package com.example.app_comedor.presentacion.screens.menu

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app_comedor.R
import com.example.app_comedor.data.network.models.menu.toDto
import com.example.app_comedor.presentacion.common.bottomBar.BottomBarCustom
import com.example.app_comedor.presentacion.common.progresBar.CustomProgressBar
import com.example.app_comedor.presentacion.common.snackbar.CustomSnackbar
import com.example.app_comedor.presentacion.screens.menu.components.FoodMenuItem
import com.example.app_comedor.utils.ApiResult
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MenuScreen(
    navController: NavController,
    viewModel: MenuViewModel = koinViewModel<MenuViewModel>()
) {
    val response = viewModel.responseMenu
    val snackBarState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarState
            ) {
                CustomSnackbar(
                    modifier = Modifier,
                    title = it.visuals.message,
                    subTitle = "",
                    animation = R.raw.error
                )
            }
        },
        bottomBar = {
            BottomBarCustom(navController = navController)
        }
    ) { padding ->
        when (response) {
            is ApiResult.Error -> {
                LaunchedEffect(Unit) {
                    scope.launch {
                        snackBarState.showSnackbar(
                            message = response.error ?: ""
                        )
                    }
                }
            }

            is ApiResult.Loading -> CustomProgressBar()
            is ApiResult.Success -> {
                val menu = response.data?.data?.toDto()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(MaterialTheme.colorScheme.background),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "App Comedor Estudiantil Unerg",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        modifier = Modifier.padding(top = 40.dp),
                        text = "Menu Semanal",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    if (menu != null) {

                        val weekStart = try {
                            menu.weekStart.let {
                                menu.weekStart.let {
                                    Instant.parse(it).atZone(ZoneId.systemDefault()).toLocalDate()
                                }
                            }
                        } catch (e: Exception) {
                            null
                        }

                        val dishesWithDate = weekStart?.let { start ->
                            listOf(
                                start to menu.monday,
                                start.plusDays(1) to menu.tuesday,
                                start.plusDays(2) to menu.wednesday,
                                start.plusDays(3) to menu.thursday,
                                start.plusDays(4) to menu.friday
                            )
                        } ?: emptyList()

                        LazyRow(modifier = Modifier.fillMaxWidth()) {
                            items(dishesWithDate.size) { index ->
                                FoodMenuItem(
                                    menuItem = dishesWithDate[index].second,
                                    date = dishesWithDate[index].first,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

