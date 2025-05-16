package com.example.app_comedor.presentacion.screens.dish.components

import AnimatedStarRating
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch

/**
 * BottomSheet para puntuar un plato
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingBottomSheet(
    onDismiss: () -> Unit,
    onSubmit: (rating: Float, comment: String) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var rating by remember { mutableStateOf(0.0f) }
    var comment by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState,
        containerColor = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        dragHandle = { Divider(thickness = 4.dp, modifier = Modifier.width(40.dp)) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Título
            Text(
                text = "Califica el almuerzo de hoy!!",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Estrellas y puntuación
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Estrellas interactivas
                AnimatedStarRating(
                    initialRating = rating,
                    onRatingChanged = { it ->
                        rating = it
                    },
                    starSize = 40,
                    starColor = Color(0xFFFFD700), // Color dorado
                )

/*                for (i in 1..5) {
                    val isFilled = i <= rating
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .padding(end = 4.dp)
                    ) {
                        Icon(
                            imageVector = if (isFilled) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Estrella $i",
                            tint = if (isFilled) MaterialTheme.colorScheme.onBackground else Color.LightGray,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {

                                }
                        )
                    }
                }*/

                Text(
                    text = String.format("%.1f", rating),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Campo de comentarios
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Coméntanos tu Opinión del plato de hoy",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Opcional",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 8.dp),
                )

                TextField(
                    value = comment,
                    onValueChange = { comment = it },
                    placeholder = { Text("email@domain.com") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                        focusedIndicatorColor = MaterialTheme.colorScheme.background
                    )
                )
            }

            // Botón de enviar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        onSubmit(rating, comment)
                        scope.launch {
                            bottomSheetState.hide()
                            onDismiss()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFD180)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.width(200.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "enviar",
                        color = Color.Black
                    )
                }
            }
        }
    }
}

/**
 * Alerta reutilizable con mensaje e icono personalizables
 */
@Composable
fun CustomAlertDialog(
    show: Boolean,
    message: String,
    icon: ImageVector = Icons.Default.Person,
    buttonText: String = "Aceptar",
    onDismiss: () -> Unit
) {
    if (show) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Icono
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Mensaje
                    Text(
                        text = message,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFD180)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = buttonText,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

/**
 * Ejemplo de uso de los componentes
 */
@Composable
fun ExampleUsage() {
    var showRatingSheet by remember { mutableStateOf(false) }
    var showAlert by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { showRatingSheet = true }) {
            Text("Mostrar Rating BottomSheet")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            alertMessage = "Gracias, por Confirmar su Asistencia. Lo añadiremos a la lista de hoy"
            showAlert = true
        }) {
            Text("Mostrar Alerta")
        }
    }

    // BottomSheet para puntuar
    if (showRatingSheet) {
        RatingBottomSheet(
            onDismiss = { showRatingSheet = false },
            onSubmit = { rating, comment ->
                showRatingSheet = false
                alertMessage = "Gracias por tu calificación de $rating estrellas!"
                showAlert = true
            }
        )
    }

    // Alerta personalizable
    CustomAlertDialog(
        show = showAlert,
        message = alertMessage,
        icon = Icons.Default.Person,
        buttonText = "Aceptar",
        onDismiss = { showAlert = false }
    )
}