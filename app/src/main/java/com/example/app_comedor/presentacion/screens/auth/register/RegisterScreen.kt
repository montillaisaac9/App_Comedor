package com.example.app_comedor.presentacion.screens.auth.register

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app_comedor.R
import com.example.app_comedor.presentacion.common.buttons.CustomButton
import com.example.app_comedor.presentacion.common.inputs.CustomInput
import com.example.app_comedor.presentacion.common.inputs.CustomStepperInput
import com.example.app_comedor.presentacion.common.progresBar.CustomProgressBar
import com.example.app_comedor.presentacion.common.spinner.SpinnerTextField
import com.example.app_comedor.presentacion.navegation.destination.Screen
import com.example.app_comedor.presentacion.screens.auth.register.components.HeaderUnerg
import com.example.app_comedor.presentacion.screens.auth.register.components.TakeCapPicture
import com.example.app_comedor.utils.ApiResult
import com.example.app_comedor.utils.getUriForFile
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import timber.log.Timber
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = koinViewModel<RegisterViewModel>()
) {
    val focusManager = LocalFocusManager.current
    val state = viewModel.state
    var showSecondSection by remember { mutableStateOf(false) }
    var currentSection by rememberSaveable { mutableStateOf(1) }

    LaunchedEffect(true) {
        viewModel.getCarriers()
    }

    // Manejo del botón "Atrás"
    BackHandler(enabled = showSecondSection) {
        showSecondSection = false
        currentSection = 1
    }

    val snackBarState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold { paddingValues ->

        when (viewModel.carrierRespose) {
            is ApiResult.Error -> {
                LaunchedEffect(Unit) {
                    scope.launch {
                        snackBarState.showSnackbar(
                            message = viewModel.carrierRespose.error ?: ""
                        )
                    }
                }
            }

            is ApiResult.Loading -> CustomProgressBar()
            is ApiResult.Success -> {
                Column(
                    modifier = Modifier
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .padding(top = 10.dp)
                            .height(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        repeat(2) { step ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(5.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(
                                        if (currentSection == step + 1)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                    )
                            )
                            if (step == 0) Spacer(modifier = Modifier.width(8.dp))
                        }
                    }

                    // Header basado en sección actual
                    if (currentSection == 1) {
                        HeaderUnerg("Registrarse")
                    } else {
                        TakeCapPicture(
                            imageFile = state.image
                        ) {
                            viewModel.setImage(it)
                        }
                    }

                    if (!showSecondSection) {
                        currentSection = 1
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CustomInput(
                                    modifier = Modifier
                                        .fillMaxWidth(0.95f)
                                        .padding(top = 14.dp),
                                    state = state.identification,
                                    label = "Cédula",
                                    errorText = state.errMessge,
                                    trailingIcon = if (state.identification.isNotEmpty()) Icons.Outlined.Clear else null,
                                    trailingIconDescription = if (state.identification.isNotEmpty()) stringResource(
                                        R.string.clear_field
                                    ) else null,
                                    onTrailingIconClick = {
                                        viewModel.setValue(
                                            "identification",
                                            ""
                                        )
                                    },
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done,
                                    onImeAction = { focusManager.clearFocus() }
                                ) {
                                    viewModel.setValue("identification", it)
                                }

                                // Lista de carreras
                                for (index in 0 until state.numberCarriers) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth(0.95f)
                                            .padding(top = 14.dp)
                                    ) {
                                        AnimatedVisibility(state.numberCarriers > 0) {
                                            SpinnerTextField(
                                                modifier = Modifier.fillMaxWidth(),
                                                label = "Carrera ${index + 1}",
                                                list = viewModel.listCarriers,
                                                selected = viewModel.getSelectedSpinner(index),
                                                enable = true,
                                                select = { spinnerItem ->
                                                    viewModel.updateCareerSelection(
                                                        index,
                                                        spinnerItem.id.toInt()
                                                    )
                                                }
                                            )
                                        }
                                    }
                                }

                                // Botones para añadir/quitar carreras
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 5.dp),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AnimatedVisibility(state.numberCarriers > 1) {
                                        IconButton(
                                            onClick = {
                                                viewModel.removeLastCareer()
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Outlined.Remove,
                                                contentDescription = "remove"
                                            )
                                        }
                                    }
                                    IconButton(
                                        onClick = {
                                            if (state.numberCarriers < 3) {
                                                viewModel.setValue(
                                                    "numberCarriers",
                                                    state.numberCarriers + 1
                                                )
                                            }
                                        },
                                        enabled = state.numberCarriers < 3
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Add,
                                            contentDescription = "add"
                                        )
                                    }
                                }

                            }
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                CustomButton(
                                    background = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(top = 14.dp, bottom = 30.dp),
                                    text = "Siguiente",
                                    enable = state.numberCarriers > 0 && state.identification.isNotEmpty()
                                ) {
                                    showSecondSection = true
                                    currentSection = 2
                                }
                            }
                        }
                    }

                    if (showSecondSection) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CustomInput(
                                    modifier = Modifier
                                        .fillMaxWidth(0.95f)
                                        .padding(top = 14.dp),
                                    state = state.name,
                                    label = "Nombre",
                                    errorText = state.errMessge,
                                    trailingIcon = if (state.name.isNotEmpty()) Icons.Outlined.Clear else null,
                                    trailingIconDescription = if (state.name.isNotEmpty()) stringResource(
                                        R.string.clear_field
                                    ) else null,
                                    onTrailingIconClick = { viewModel.setValue("name", "") },
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next,
                                    onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                                ) { input -> viewModel.setValue("name", input) }

                                CustomInput(
                                    modifier = Modifier
                                        .fillMaxWidth(0.95f)
                                        .padding(top = 14.dp),
                                    state = state.email,
                                    label = stringResource(R.string.correo_electronico),
                                    errorText = viewModel.state.errMessge,
                                    trailingIcon = if (state.email.isNotEmpty()) Icons.Outlined.Clear else null,
                                    trailingIconDescription = if (state.email.isNotEmpty()) stringResource(
                                        R.string.clear_field
                                    ) else null,
                                    onTrailingIconClick = { viewModel.setValue("email", "") },
                                    keyboardType = KeyboardType.Email,
                                    imeAction = ImeAction.Next,
                                    onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                                ) { viewModel.setValue("email", it) }

                                CustomInput(
                                    modifier = Modifier
                                        .fillMaxWidth(0.95f)
                                        .padding(top = 14.dp),
                                    state = state.securityWord,
                                    label = "Palabra de seguridad",
                                    errorText = state.errMessge,
                                    trailingIcon = if (state.securityWord.isNotEmpty()) Icons.Outlined.Clear else null,
                                    trailingIconDescription = if (state.securityWord.isNotEmpty()) stringResource(
                                        R.string.clear_field
                                    ) else null,
                                    onTrailingIconClick = {
                                        viewModel.setValue(
                                            "securityWord",
                                            ""
                                        )
                                    },
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next,
                                    onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                                ) { input -> viewModel.setValue("securityWord", input) }

                                CustomInput(
                                    modifier = Modifier
                                        .fillMaxWidth(0.95f)
                                        .padding(top = 14.dp),
                                    state = state.password,
                                    label = stringResource(R.string.contrase_a),
                                    errorText = state.errMessge,
                                    trailingIcon = if (state.password.isNotEmpty()) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                    trailingIconDescription = if (state.password.isNotEmpty()) stringResource(
                                        R.string.show_password
                                    ) else stringResource(
                                        R.string.hide_password
                                    ),
                                    onTrailingIconClick = {},
                                    keyboardType = KeyboardType.Password,
                                    imeAction = ImeAction.Next,
                                    onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                                ) { viewModel.setValue("password", it) }

                                CustomInput(
                                    modifier = Modifier
                                        .fillMaxWidth(0.95f)
                                        .padding(top = 14.dp),
                                    state = state.confirmPassword,
                                    label = "Confirmar contraseña",
                                    errorText = state.errMessge,
                                    trailingIcon = if (state.confirmPassword.isNotEmpty()) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                    trailingIconDescription = if (state.confirmPassword.isNotEmpty()) stringResource(
                                        R.string.show_password
                                    ) else stringResource(R.string.hide_password),
                                    onTrailingIconClick = {},
                                    keyboardType = KeyboardType.Password,
                                    imeAction = ImeAction.Done,
                                    onImeAction = { focusManager.clearFocus() }
                                ) { viewModel.setValue("confirmPassword", it) }
                            }
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                CustomButton(
                                    background = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(top = 14.dp, bottom = 30.dp),
                                    text = "Registrarse",
                                    enable = state.email.isNotEmpty() && state.password.isNotEmpty()
                                ) {
                                    viewModel.register()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    when (viewModel.registerResponse) {
        is ApiResult.Error -> {
            LaunchedEffect(Unit) {
                scope.launch {
                    snackBarState.showSnackbar(
                        message = viewModel.registerResponse?.error ?: ""
                    )
                }
            }
        }
        is ApiResult.Loading -> CustomProgressBar()
        is ApiResult.Success -> {
            navController.navigate(Screen.LoginScreen.route) {
                popUpTo(Screen.LoginScreen.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
        null -> {}
    }
}