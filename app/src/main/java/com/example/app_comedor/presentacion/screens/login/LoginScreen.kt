package com.example.app_comedor.presentacion.screens.login


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
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToResetPassword: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = koinViewModel<LoginViewModel>()
) {
    val focusManager = LocalFocusManager.current
    val stateLogin = viewModel.state

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
                    state = stateLogin.email,
                    label = stringResource(R.string.correo_electronico),
                    errorText = viewModel.state.errMsgEmail,
                    trailingIcon = if (stateLogin.email.isNotEmpty()) Icons.Outlined.Clear else null,
                    trailingIconDescription = if (stateLogin.email.isNotEmpty()) stringResource(R.string.clear_field) else null,
                    onTrailingIconClick = { viewModel.setTextEmail("") },
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    onImeAction = { focusManager.moveFocus(FocusDirection.Down) },
                ) {
                    viewModel.setTextEmail(it)
                }

                Spacer(modifier = Modifier.height(10.dp))
                CustomInput(
                    modifier = Modifier.fillMaxWidth(0.95f),
                    state = stateLogin.password,
                    label = stringResource(R.string.contrase_a),
                    errorText = stateLogin.errMsgPassword,
                    trailingIcon = if (stateLogin.password.isNotEmpty()) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                    trailingIconDescription = if (stateLogin.password.isNotEmpty()) stringResource(R.string.show_password) else stringResource(
                        R.string.hide_password
                    ),
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    onImeAction = { focusManager.clearFocus() }
                ) {
                    viewModel.setTextPassword(it)
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
                    enable = stateLogin.email.isNotEmpty() && stateLogin.password.isNotEmpty()
                ) {
                    viewModel.login()
                }
            }
        }
    }
    when(viewModel.loginResponse){
        is ApiResult.Error -> TODO()
        is ApiResult.Loading -> TODO()
        is ApiResult.Success -> TODO()
        null -> {}
    }
}