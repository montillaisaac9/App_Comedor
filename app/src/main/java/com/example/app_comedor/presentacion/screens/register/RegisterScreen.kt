package com.example.app_comedor.presentacion.screens.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.app_comedor.R
import com.example.app_comedor.presentacion.common.buttons.CustomButton
import com.example.app_comedor.presentacion.common.inputs.CustomInput
import com.example.app_comedor.utils.ApiResult
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToResetPassword: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel<RegisterViewModel>()
) {
    val focusManager = LocalFocusManager.current
    val state = viewModel.state

    Scaffold { paddingValues ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(paddingValues).fillMaxSize()
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
                    "Iniciar Sesión",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.fillMaxHeight(0.15f))
                CustomInput(
                    modifier = Modifier.fillMaxWidth(0.95f),
                    state = state.email,
                    label = stringResource(R.string.correo_electronico),
                    errorText = viewModel.state.errMessge,
                    trailingIcon = if (state.email.isNotEmpty()) Icons.Outlined.Clear else null,
                    trailingIconDescription = if (state.email.isNotEmpty()) stringResource(R.string.clear_field) else null,
                    onTrailingIconClick = { viewModel.setValue("email","") },
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    onImeAction = { focusManager.moveFocus(FocusDirection.Down) },
                ) {
                    viewModel.setValue("email",it)
                }
                // Campo para la Identificación
                CustomInput(
                    modifier = Modifier.fillMaxWidth(0.95f),
                    state = state.identification,
                    label = "cedula",
                    errorText = state.errMessge,
                    trailingIcon = if (state.identification.isNotEmpty()) Icons.Outlined.Clear else null,
                    trailingIconDescription = if (state.identification.isNotEmpty()) stringResource(R.string.clear_field) else null,
                    onTrailingIconClick = { viewModel.setValue("identification", "") },
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                ) { input ->
                    viewModel.setValue("identification", input)
                }

                // Campo para el Nombre
                CustomInput(
                    modifier = Modifier.fillMaxWidth(0.95f),
                    state = state.name,
                    label = "nombre",
                    errorText = state.errMessge,
                    trailingIcon = if (state.name.isNotEmpty()) Icons.Outlined.Clear else null,
                    trailingIconDescription = if (state.name.isNotEmpty()) stringResource(R.string.clear_field) else null,
                    onTrailingIconClick = { viewModel.setValue("name", "") },
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                ) { input ->
                    viewModel.setValue("name", input)
                }

                // Campo para la Palabra de Seguridad
                CustomInput(
                    modifier = Modifier.fillMaxWidth(0.95f),
                    state = state.securityWord,
                    label = "palabra de seguridad",
                    errorText = state.errMessge,
                    trailingIcon = if (state.securityWord.isNotEmpty()) Icons.Outlined.Clear else null,
                    trailingIconDescription = if (state.securityWord.isNotEmpty()) stringResource(R.string.clear_field) else null,
                    onTrailingIconClick = { viewModel.setValue("securityWord", "") },
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                    onImeAction = { focusManager.clearFocus() }
                ) { input ->
                    viewModel.setValue("securityWord", input)
                }
                Spacer(modifier = Modifier.height(10.dp))
                CustomInput(
                    modifier = Modifier.fillMaxWidth(0.95f),
                    state = state.password,
                    label = stringResource(R.string.contrase_a),
                    errorText = state.errMessge,
                    trailingIcon = if (state.password.isNotEmpty()) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                    trailingIconDescription = if (state.password.isNotEmpty()) stringResource(R.string.show_password) else stringResource(
                        R.string.hide_password
                    ),
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    onImeAction = { focusManager.clearFocus() }
                ) {
                    viewModel.setValue("password",it)
                }
                CustomInput(
                    modifier = Modifier.fillMaxWidth(0.95f),
                    state = state.password,
                    label = stringResource(R.string.contrase_a),
                    errorText = state.errMessge,
                    trailingIcon = if (state.confirmPassword.isNotEmpty()) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                    trailingIconDescription = if (state.confirmPassword.isNotEmpty()) stringResource(R.string.show_password) else stringResource(
                        R.string.hide_password
                    ),
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    onImeAction = { focusManager.clearFocus() }
                ) {
                    viewModel.setValue("confirmPassword",it)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CustomButton(
                    background = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(10.dp, bottom = 30.dp),
                    text = "Iniciar Sesión",
                    enable = state.email.isNotEmpty() && state.password.isNotEmpty()
                ) {
                }
            }
        }
    }
}