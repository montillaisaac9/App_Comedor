package com.example.app_comedor.presentacion.screens.auth.resetPassword


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app_comedor.R
import com.example.app_comedor.data.network.models.auth.toDto
import com.example.app_comedor.presentacion.common.buttons.CustomButton
import com.example.app_comedor.presentacion.common.inputs.CustomInput
import com.example.app_comedor.presentacion.common.progresBar.CustomProgressBar
import com.example.app_comedor.presentacion.common.snackbar.CustomSnackbar
import com.example.app_comedor.presentacion.navegation.destination.Screen
import com.example.app_comedor.presentacion.screens.auth.login.LoginViewModel
import com.example.app_comedor.utils.ApiResult
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ScreenResetPassword(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ResetPasswordViewmodel = koinViewModel<ResetPasswordViewmodel>()
) {
    val focusManager = LocalFocusManager.current
    val state = viewModel.state
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
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(R.drawable.unerg),
                    contentDescription = stringResource(R.string.app_name),
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(top = 30.dp)
                )
                Spacer(modifier = Modifier.fillMaxHeight(0.15f))
                Text(
                    "Recuperar Contraseña",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.fillMaxHeight(0.15f))
                CustomInput(
                    modifier = Modifier.fillMaxWidth(0.95f),
                    state = state.email,
                    label = stringResource(R.string.correo_electronico),
                    errorText ="",
                    trailingIcon = if (state.email.isNotEmpty()) Icons.Outlined.Clear else null,
                    trailingIconDescription = if (state.email.isNotEmpty()) stringResource(R.string.clear_field) else null,
                    onTrailingIconClick = { viewModel.setValue("email", "") },
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    onImeAction = { focusManager.moveFocus(FocusDirection.Down) },
                ) {
                    viewModel.setValue("email", it)
                }
                CustomInput(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .padding(top = 14.dp),
                    state = state.wordSecurity,
                    label = "Palabra de seguridad",
                    errorText = "",
                    trailingIcon = if (state.wordSecurity.isNotEmpty()) Icons.Outlined.Clear else null,
                    trailingIconDescription = if (state.wordSecurity.isNotEmpty()) stringResource(
                        R.string.clear_field
                    ) else null,
                    onTrailingIconClick = {
                        viewModel.setValue(
                            "wordSecurity",
                            ""
                        )
                    },
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                ) { input -> viewModel.setValue("wordSecurity", input) }

                CustomInput(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .padding(top = 14.dp),
                    state = state.password,
                    label = stringResource(R.string.contrase_a),
                    errorText = "",
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
                    errorText = "",
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                CustomButton(
                    background = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(10.dp, bottom = 30.dp),
                    text = "Recuperar Contraseña",
                    enable = state.email.isNotEmpty() && state.password.isNotEmpty() && state.wordSecurity.isNotEmpty() && state.confirmPassword.isNotEmpty(),
                ) {
                    viewModel.resetPassword()
                }
                Row(
                    modifier = Modifier.fillMaxWidth(0.95f),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        navController.navigate(Screen.RegisterScreen.route) {
                            launchSingleTop = true
                        }
                    }) {
                        Text("No tengo Cuenta", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
    when (viewModel.resetPasswordResponse) {
        is ApiResult.Error -> {
            LaunchedEffect(Unit) {
                scope.launch {
                    snackBarState.showSnackbar(
                        message = viewModel.resetPasswordResponse?.error ?: ""
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