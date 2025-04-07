package com.example.app_comedor.presentacion.common.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun CustomInput(
    state: String,
    label: String,
    errorText: String,
    modifier: Modifier = Modifier,
    trailingIcon: ImageVector? = null,
    trailingIconDescription: String? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    borderColor: Color? = null,
    readOnly: Boolean = false,
    enable: Boolean = true,
    textAlign: TextAlign = TextAlign.Unspecified,
    onImeAction: (() -> Unit)? = null,
    onStateChange: (String) -> Unit = {},
) {
    val isError = errorText.isNotEmpty()
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = state,
        onValueChange = onStateChange,
        textStyle = MaterialTheme.typography.bodySmall.copy(
            color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
            textAlign = textAlign
        ),
        enabled = enable,
        readOnly = readOnly,
        singleLine = true,
        label = {
            Text(
                text = label,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall,
            )
        },
        shape = RoundedCornerShape(10.dp),
        isError = isError,
        visualTransformation = if (!passwordVisible && keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = trailingIcon?.let {
            {
                if (keyboardType == KeyboardType.Password) {
                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = trailingIconDescription
                        )
                    }
                } else {
                    IconButton(onClick = { onTrailingIconClick?.invoke() }) {
                        Icon(imageVector = it, contentDescription = trailingIconDescription)
                    }
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onAny = { onImeAction?.invoke() }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
            focusedLabelColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            focusedPlaceholderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            focusedTextColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            focusedBorderColor = if (isError) MaterialTheme.colorScheme.error else borderColor ?: MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = if (isError) MaterialTheme.colorScheme.error else borderColor ?:  MaterialTheme.colorScheme.onPrimary,
            disabledBorderColor = MaterialTheme.colorScheme.onPrimary,
            disabledLabelColor = MaterialTheme.colorScheme.onPrimary,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
            selectionColors = TextSelectionColors(
                handleColor = MaterialTheme.colorScheme.primary,
                backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            )
        )
    )
    if (isError) {
        Text(
            text = errorText,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun CustomInput(
    state: TextFieldValue,
    label: String,
    errorText: String,
    modifier: Modifier = Modifier,
    trailingIcon: ImageVector? = null,
    trailingIconDescription: String? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    readOnly: Boolean = false,
    textAlign: TextAlign = TextAlign.Unspecified,
    onImeAction: (() -> Unit)? = null,
    onStateChange: (TextFieldValue) -> Unit = {},
) {
    val isError = errorText.isNotEmpty()
    var passwordVisible by remember { mutableStateOf(false) }


    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = state,
        onValueChange = onStateChange,
        textStyle = MaterialTheme.typography.bodySmall.copy(
            color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
            textAlign = textAlign
        ),
        readOnly = readOnly,
        singleLine = true,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
            )
        },
        shape = RoundedCornerShape(10.dp),
        isError = isError,
        visualTransformation = if (!passwordVisible && keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = trailingIcon?.let {
            {
                if (keyboardType == KeyboardType.Password) {
                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = trailingIconDescription
                        )
                    }
                } else {
                    IconButton(onClick = { onTrailingIconClick?.invoke() }) {
                        Icon(imageVector = it, contentDescription = trailingIconDescription)
                    }
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onAny = { onImeAction?.invoke() }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
            focusedLabelColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            focusedPlaceholderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            focusedTextColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            focusedBorderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimary,
            selectionColors = TextSelectionColors(
                handleColor = MaterialTheme.colorScheme.primary,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            )
        )
    )
    if (isError) {
        Text(
            text = errorText,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun CustomStepperInput(
    value: Int,
    onValueChange: (Int) -> Unit,
    label: String,
    range: IntRange = 0..3,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            IconButton(
                onClick = {
                    if (value > range.first) onValueChange(value - 1)
                },
                enabled = value > range.first
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Disminuir"
                )
            }

            Text(
                text = value.toString(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            IconButton(
                onClick = {
                    if (value < range.last) onValueChange(value + 1)
                },
                enabled = value < range.last
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Aumentar"
                )
            }
        }
    }
}
