import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt
import android.media.MediaPlayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import com.example.app_comedor.R

@Composable
fun AnimatedStarRating(
    initialRating: Float = 0f,
    onRatingChanged: (Float) -> Unit = {},
    starSize: Int = 30,
    starSpacing: Int = 4,
    starColor: Color = MaterialTheme.colorScheme.primary,
    starBorderColor: Color = Color.LightGray,
    animationDuration: Int = 100,
    enableSound: Boolean = true
) {
    val context = LocalContext.current
    var size by remember { mutableStateOf(IntSize.Zero) }

    // Sonidos para diferentes calificaciones
    val starClickSound = remember { MediaPlayer.create(context, R.raw.shine) }

    // Estado para la calificación actual con precisión decimal
    var rating by remember { mutableStateOf(initialRating) }

    // Estado para las animaciones de cada estrella
    val fillAnimations = remember {
        List(5) { index ->
            val initialValue = when {
                index + 1 <= initialRating.toInt() -> 1f
                index < initialRating && index + 1 > initialRating -> initialRating - index
                else -> 0f
            }
            Animatable(initialValue)
        }
    }

    // Actualizar las animaciones cuando cambia la calificación
    LaunchedEffect(rating) {
        onRatingChanged(rating)

        // Reproducir sonido cuando se cambia la calificación
        if (enableSound) {
            try {
                starClickSound.seekTo(0)
                starClickSound.start()
            } catch (e: Exception) {
                // Manejar excepciones de reproducción de sonido
            }
        }

        // Animar cada estrella individualmente
        fillAnimations.forEachIndexed { index, animatable ->
            val targetValue = when {
                index + 1 <= rating.toInt() -> 1f
                index < rating && index + 1 > rating -> rating - index
                else -> 0f
            }

            animatable.animateTo(
                targetValue = targetValue,
                animationSpec = tween(
                    durationMillis = animationDuration,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }

    Column {
        Box(
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    size = coordinates.size
                }
                .padding(vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures { change, dragAmount ->
                            change.consume()
                            val starWidth = (starSize + starSpacing)
                            val position = change.position.x / starWidth

                            // Calcular calificación con un decimal
                            val newRating = (position * 10).roundToInt() / 10f
                            rating = newRating.coerceIn(0f, 5f)
                        }
                    }
            ) {
                // Estrellas interactivas con animación de llenado
                for (i in 1..5) {
                    Box(
                        modifier = Modifier
                            .size(starSize.dp)
                            .padding(end = starSpacing.dp)
                    ) {
                        // Estrella vacía (contorno)
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = "Estrella $i vacía",
                            tint = starBorderColor,
                            modifier = Modifier
                                .fillMaxSize()
                                .zIndex(1f)
                        )

                        // Estrella llena con animación de ancho
                        val fillLevel = fillAnimations[i-1].value
                        if (fillLevel > 0f) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(fillLevel)
                                    .fillMaxHeight()
                                    .zIndex(2f)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = "Estrella $i llena",
                                    tint = starColor,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        // Área clickeable por encima de todo
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .zIndex(3f)
                                .clickable {
                                    // Alternar entre valores completos, mitad y cuartos
                                    rating = when {
                                        (rating > i - 0.25f && rating < i + 0.25f) -> i - 0.5f
                                        (rating >= i - 0.5f && rating <= i - 0.26f) -> i - 0.25f
                                        (rating >= i - 0.75f && rating <= i - 0.51f) -> i - 0.75f
                                        else -> i.toFloat()
                                    }
                                }
                        )
                    }
                }

                // Mostrar el valor numérico de la calificación
                Text(
                    text = String.format("%.1f", rating),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }

    // Liberar recursos cuando se desmonte el componente
    DisposableEffect(Unit) {
        onDispose {
            starClickSound.release()
        }
    }
}