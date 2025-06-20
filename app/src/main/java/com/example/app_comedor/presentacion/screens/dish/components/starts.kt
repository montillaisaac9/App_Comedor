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
import android.util.Log
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.unit.IntSize
import com.example.app_comedor.R
import timber.log.Timber
@Composable
fun AnimatedStarRating(
    initialRating: Float = 0f,
    onRatingChanged: (Float) -> Unit = {},
    starSize: Int = 30,
    starSpacing: Int = 4,
    starColor: Color = MaterialTheme.colorScheme.primary, // Color de relleno para la estrella llena
    starBorderColor: Color = Color.LightGray, // Color para la estrella de contorno (vacía)
    animationDuration: Int = 300, // Duración de la animación de llenado
    enableSound: Boolean = true,
    clickable: Boolean = false
) {
    val context = LocalContext.current
    val density = LocalDensity.current

    val starClickSound = remember(context) {
        try {
            MediaPlayer.create(context, R.raw.shine)
        } catch (e: Exception) {
            Log.e("AnimatedStarRating", "Error creating MediaPlayer", e)
            null
        }
    }

    var rating by remember { mutableStateOf(initialRating.coerceIn(0f, 5f)) }

    val fillAnimations = remember {
        List(5) { index ->
            val initialFill = when {
                index + 1 <= initialRating.toInt() -> 1f
                index < initialRating && index + 1 > initialRating -> initialRating - index
                else -> 0f
            }
            Animatable(initialFill.coerceIn(0f, 1f))
        }
    }

    LaunchedEffect(rating) {
        onRatingChanged(rating)
        fillAnimations.forEachIndexed { index, animatable ->
            val targetFill = when {
                index + 1 <= rating.toInt() -> 1f
                index < rating && index + 1 > rating -> (rating - index).coerceIn(0f, 1f)
                else -> 0f
            }
            if (animatable.value != targetFill) {
                animatable.animateTo(
                    targetValue = targetFill,
                    animationSpec = tween(
                        durationMillis = animationDuration,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        }
    }

    fun playSound() {
        if (enableSound && starClickSound != null) {
            try {
                if (starClickSound.isPlaying) {
                    starClickSound.stop()
                    starClickSound.prepare()
                }
                starClickSound.seekTo(0)
                starClickSound.start()
            } catch (e: Exception) {
                Log.e("AnimatedStarRating", "Error playing sound", e)
            }
        }
    }

    Column {
        Box(modifier = Modifier
            .padding(vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, _ ->
                        change.consume()
                        val starTotalWidthPx = with(density) { (starSize.dp + starSpacing.dp).toPx() }
                        if (starTotalWidthPx == 0f) return@detectHorizontalDragGestures

                        val rawPosition = change.position.x / starTotalWidthPx
                        // Ajustar para que el cálculo del rating sea más intuitivo al arrastrar sobre una estrella
                        // (cada estrella ocupa una unidad de 'rawPosition')
                        val newCalculatedRating = (rawPosition * 10).roundToInt() / 10f
                        val newCoercedRating = newCalculatedRating.coerceIn(0f, 5f)

                        if (newCoercedRating != rating) {
                            rating = newCoercedRating
                            playSound()
                        }
                    }
                }
            ) {
                for (i in 1..5) {
                    val starIndex = i - 1
                    Box(
                        modifier = Modifier
                            .size(starSize.dp)
                            .padding(end = if (i < 5) starSpacing.dp else 0.dp)
                    ) {
                        // Estrella vacía (contorno) - Capa inferior
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = "Estrella $i vacía",
                            tint = starBorderColor,
                            modifier = Modifier
                                .fillMaxSize()
                                .zIndex(1f)
                        )

                        // Estrella llena (SVG) - Capa intermedia, recortada dinámicamente
                        val fillLevel = fillAnimations[starIndex].value
                        if (fillLevel > 0f) {
                            Icon(
                                painter = painterResource(R.drawable.star24px), // ESTE DEBE SER UN SVG DE ESTRELLA RELLENA
                                contentDescription = "Estrella $i llena (fracción: ${String.format("%.2f", fillLevel)})",
                                tint = starColor, // El color de la parte rellena
                                modifier = Modifier
                                    .fillMaxSize() // El Icono intenta llenar el Box de la celda de estrella
                                    .graphicsLayer( // Aplicamos el recorte aquí
                                        clip = true, // Habilitar recorte
                                        shape = GenericShape { size, _ ->
                                            addRect(Rect(left = 0f, top = 0f, right = size.width * fillLevel, bottom = size.height))
                                        }
                                    )
                                    .zIndex(2f) // Encima del contorno
                            )
                        }

                        // Área clickeable - Capa superior
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .zIndex(3f) // Encima de todo para asegurar la detección del clic
                                .clickable {
                                    if (!clickable) {return@clickable}
                                    else {
                                    val oldRating = rating
                                    val newClickedRating = when { // Tu lógica de clic original
                                        (oldRating > i - 0.25f && oldRating < i + 0.25f) -> i - 0.5f
                                        (oldRating >= i - 0.5f && oldRating <= i - 0.26f) -> i - 0.25f
                                        (oldRating >= i - 0.75f && oldRating <= i - 0.51f) -> i - 0.75f
                                        else -> i.toFloat()
                                    }.coerceIn(0f, 5f)

                                    if (newClickedRating != oldRating) {
                                        rating = newClickedRating
                                        playSound()
                                        }
                                    }
                                }
                        )
                    }
                }
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

    DisposableEffect(Unit) {
        onDispose {
            starClickSound?.release()
        }
    }
}