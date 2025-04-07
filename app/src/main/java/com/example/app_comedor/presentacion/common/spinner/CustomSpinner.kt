package com.example.app_comedor.presentacion.common.spinner

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.app_comedor.presentacion.theme.mediumGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerTextField(
    modifier: Modifier = Modifier,
    label: String = "Seleccione un estado",
    list: List<SpinnerItem>,
    selected: SpinnerItem? = null,
    enable: Boolean = true,
    isEllipsis: Boolean = false,
    height: Int = 60,
    onRefresh: () -> Unit = {},
    onChangeLabel: Boolean = false,
    select: (SpinnerItem) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(selected) }
    val prefixRegex = Regex("^(Select a |Seleccione una |Seleccione un )")


    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OutlinedTextField(
            singleLine = true,
            enabled = enable,
            value = selectedItem?.label ?: "",
            onValueChange = { },
            readOnly = true,
            textStyle = MaterialTheme.typography.bodySmall,
            label = {
                    if (onChangeLabel && selected != null) Text(
                        label.replace(prefixRegex, ""),
                            style = MaterialTheme.typography.bodySmall
                        )
                    else if (onChangeLabel && !expanded)Text(
                              label,  color = mediumGray, style = MaterialTheme.typography.bodySmall
                          )
                    else Text(
                        label, style = MaterialTheme.typography.bodySmall
                    )
                    },
            modifier = modifier
                .fillMaxWidth()
                .menuAnchor()
                .height(height.dp)
                .clickable {
                    expanded = !expanded
                },
            trailingIcon = {
                if (list.isEmpty()) {
                    IconButton(onClick = {
                        onRefresh()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Replay,
                            contentDescription = "Reset"
                        )
                    }
                } else {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            },
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                focusedLabelColor =  MaterialTheme.colorScheme.primary,
                focusedPlaceholderColor =  MaterialTheme.colorScheme.primary,
                focusedTextColor =  MaterialTheme.colorScheme.primary,
                focusedBorderColor =  MaterialTheme.colorScheme.primary,
                unfocusedBorderColor =  MaterialTheme.colorScheme.onPrimary,
                selectionColors = TextSelectionColors(
                    handleColor = MaterialTheme.colorScheme.primary,
                    backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                )
            )
        )
        if (list.isNotEmpty() && !list.contains(SpinnerItem("", ""))) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                list.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            if (isEllipsis) {
                                    Text(
                                        item.label,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1,
                                        style = MaterialTheme.typography.bodySmall
                                    )

                            } else Text(
                                item.label,
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        onClick = {
                            selectedItem = item
                            expanded = false
                            select(item)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                    )
                }
            }
        }
    }
}
