package com.example.app_comedor.presentacion.common.snackbar

import com.example.app_comedor.R
import com.example.app_comedor.presentacion.theme.AppComedorTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun CustomSnackbar(
    modifier: Modifier = Modifier,
    title:String,
    subTitle:String,
    animation:Int,
    size:Dp = 100.dp
){

    Snackbar(
        modifier = modifier.padding(5.dp),
        containerColor= MaterialTheme.colorScheme.onSecondaryContainer,
        contentColor = MaterialTheme.colorScheme.onBackground,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
            ) {
            Column(Modifier.weight(1f)) {
                Text(
                    title,
                    style  = MaterialTheme.typography.bodyLarge
                )
                Text(subTitle)
            }
            CustomLottieAnimation(
                animation= animation,
                size = size
            )
        }
    }
}

@Composable
fun CustomSnackbar(
    modifier: Modifier = Modifier,
    title:String,
){

    Snackbar(
        modifier = modifier.padding(5.dp),
        containerColor= MaterialTheme.colorScheme.onSecondaryContainer,
        contentColor = MaterialTheme.colorScheme.onBackground,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    title,
                    style  = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
    }
}

@Composable
fun CustomLottieAnimation(
    modifier: Modifier = Modifier,
    animation:Int,
    size:Dp,
    speed:Float = 1f
){
    val lottie by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(animation))
    LottieAnimation(
        speed = speed,
        modifier= modifier.size(size),
        composition = lottie,
        iterations = LottieConstants.IterateForever
    )
}

@Preview
@Composable
fun CustomSnackbarPreview(){
    AppComedorTheme {
        CustomSnackbar(title="Mensaje", subTitle = "lkfgjslghlfg", animation = R.raw.error)
    }
}