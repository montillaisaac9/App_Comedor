package com.example.app_comedor.presentacion.common.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String = "test",
    background: Color,
    delayTime: Long = 500L,
    enable: Boolean = true,
    onclick: () -> Unit = {},
) {

    var clicked by remember { mutableStateOf(false) }

    LaunchedEffect(clicked) {
        if (clicked) {
            delay(delayTime)
            onclick()
            clicked = false
        }
    }

    val bg = if (enable) background else Color.Gray

    Button(
        enabled = enable && !clicked,
        onClick = { clicked = true },
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        ),
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(bg)
            .fillMaxWidth()


    ) {
        Text(text = text, color = Color.White)
    }
}