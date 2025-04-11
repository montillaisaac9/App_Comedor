package com.example.app_comedor.presentacion.screens.menu.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.app_comedor.presentacion.screens.menu.MenuItem


@Composable
fun FoodMenuItem(
    menuItem: MenuItem,
    modifier: Modifier = Modifier,
    width: Float = 0.9f
) {
    Card(
        modifier = modifier
            .fillMaxHeight(0.95f)
            .width((LocalConfiguration.current.screenWidthDp.dp * width.toFloat()).coerceAtMost(LocalConfiguration.current.screenWidthDp.dp)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(menuItem.url)
                    .crossfade(true)
                    .scale(Scale.FILL)
                    .build(),
                contentDescription = menuItem.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(8.dp),
            ) {
                Column {
                    Text(
                        text = menuItem.title,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = menuItem.time,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFFAE7B9))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Text(
                        text = "ver menú",
                        color = Color(0xFF8B4513),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
