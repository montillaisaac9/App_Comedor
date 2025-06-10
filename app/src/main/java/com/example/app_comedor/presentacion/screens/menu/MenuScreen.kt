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
import com.example.app_comedor.presentacion.navegation.destination.Screen
import com.example.app_comedor.presentacion.screens.menu.components.FoodMenuItem
import com.example.app_comedor.utils.ApiResult
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MenuScreen(
    navController: NavController,
    viewModel: MenuViewModel = koinViewModel<MenuViewModel>()
) {
    val response = viewModel.responseMenu
    val snackBarState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.getMenu()
    }

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
                viewModel.getLocalMenu()
                val menu = viewModel.localMenu
                Timber.e("menu local ${menu}")
                LaunchedEffect(Unit) {
                    scope.launch {
                        snackBarState.showSnackbar(
                            message = response.error ?: ""
                        )
                    }
                }
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
                    LazyRow(modifier = Modifier.fillMaxWidth()) {
                        items(menu.size) { index ->
                            FoodMenuItem(
                                menuItem = menu[index],
                                modifier = Modifier.padding(8.dp),
                                onclick = {
                                    navController.navigate("Dish_Screen/${menu[index].dish?.id}"){
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
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
                        viewModel.saveLocalMenu(menu.menuItems)
                        LazyRow(modifier = Modifier.fillMaxWidth()) {
                            items(menu.menuItems.size) { index ->
                                FoodMenuItem(
                                    menuItem = menu.menuItems[index],
                                    modifier = Modifier.padding(8.dp),
                                    onclick = {
                                        navController.navigate("Dish_Screen/${menu.menuItems[index].dish?.id}")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

