package com.example.app_comedor.presentacion.screens.dish

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.app_comedor.data.network.models.menu.DishDTO
import com.example.app_comedor.data.network.models.menu.MenuItemDTO
import com.example.app_comedor.presentacion.common.topBar.CustomTopBar
import com.example.app_comedor.presentacion.screens.dish.components.CustomAlertDialog
import com.example.app_comedor.presentacion.screens.dish.components.RatingBottomSheet
import com.example.app_comedor.presentacion.screens.menu.MenuViewModel
import com.example.app_comedor.utils.HOST
import org.koin.compose.viewmodel.koinViewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun DishScreen(
    navController: NavController,
    dishId: Int? = null,
    viewModel: DishViewModel = koinViewModel<DishViewModel>()
) {

    LaunchedEffect(dishId) {
        Timber.e("ID: $dishId")
        dishId?.let { viewModel.getLocalDish(dishId) }
    }

    val dish = viewModel.dish
    // Estados para controlar las alertas y el BottomSheet
    var showRatingSheet by remember { mutableStateOf(false) }
    var showAttendConfirmAlert by remember { mutableStateOf(false) }
    var showRatingSuccessAlert by remember { mutableStateOf(false) }

    dish?.let {
        Scaffold(
            topBar = {
                CustomTopBar(
                    title = "Menu Diario",
                ) {
                    navController.popBackStack()
                }
            }
        ) { paddingValues ->
            DishDetailContent(
                dish = dish,
                onAttendClick = { showAttendConfirmAlert = it },
                onRateClick = { showRatingSheet = it },
                modifier = Modifier.padding(paddingValues)
            )

            // BottomSheet para puntuación
            if (showRatingSheet) {
                RatingBottomSheet(
                    onDismiss = { showRatingSheet = false },
                    onSubmit = { rating, comment ->
                        showRatingSheet = false
                        showRatingSuccessAlert = true
                    }
                )
            }

            // Alerta de confirmación de asistencia
            CustomAlertDialog(
                show = showAttendConfirmAlert,
                message = "Gracias, por Confirmar su Asistencia. Lo añadiremos a la lista de hoy",
                icon = Icons.Default.Person,
                buttonText = "Aceptar",
                onDismiss = { showAttendConfirmAlert = false }
            )

            // Alerta de calificación exitosa
            CustomAlertDialog(
                show = showRatingSuccessAlert,
                message = "¡Comentario y puntuación guardados exitosamente!",
                icon = Icons.Default.CheckCircle,
                buttonText = "Aceptar",
                onDismiss = { showRatingSuccessAlert = false }
            )
        }
    }
}




@Composable
fun DishDetailContent(
    dish: DishDTO,
    onAttendClick: (Boolean) -> Unit,
    onRateClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // Imagen del plato
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(dish.photo.replace("http://localhost:3000/",HOST))
                .crossfade(true)
                .build(),
            contentDescription = dish.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        )

        // Información del plato
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Título y fecha
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = dish.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                val currentDate = SimpleDateFormat("dd/MM/yyyy - HH:mm a", Locale.getDefault())
                    .format(Calendar.getInstance().time)

                Text(
                    text = currentDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Descripción
            Text(
                text = dish.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Información nutricional
            Text(
                text = "Calorías totales aproximadas:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Indicador de calorías
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.background, shape = CircleShape)
                        .border(5.dp, color = Color(0xFF9C27B0), shape = CircleShape)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "puede contener ${dish.calories} kcal.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "puede contener carbohydratos entre ${dish.carbohydrates} gr.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "puede contener grasas entre ${dish.fats} gr.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "puede contener proteinas entre ${dish.proteins} gr.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Puntuación
            Text(
                text = "Puntuación:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Estrellas
                repeat(5) { index ->
                    val color = if (index < 4) Color(0xFFFFB300) else Color.LightGray
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Rating numérico
                Text(
                    text = "4.7 (${dish.votesCount})",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Horario
            Text(
                text = "7:00 AM a 11:00 AM",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { onAttendClick(true) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFF3E0)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Asistir",
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { onRateClick(true) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFF3E0)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Puntuar",
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}