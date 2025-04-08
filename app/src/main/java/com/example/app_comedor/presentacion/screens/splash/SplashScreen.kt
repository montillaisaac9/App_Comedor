package com.example.app_comedor.presentacion.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.app_comedor.R
import com.example.app_comedor.presentacion.navegation.destination.Screen
import com.example.app_comedor.presentacion.screens.splash.components.AnimationSplashContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = koinViewModel<SplashViewModel>()
) {
    LaunchedEffect(viewModel) {
        viewModel.verifyUser()
    }

    val scaleAnimation: Animatable<Float, AnimationVector1D> =
        remember { Animatable(initialValue = 0f) }


    if (viewModel.responseDataBase!= null){
        AnimationSplashContent(
            scaleAnimation = scaleAnimation,
            durationMillisAnimation = 1000,
            delayScreen = 100L
        ){
            navController.navigate(route = "dashboard_graph") {
                popUpTo(route = Screen.SplashScreen.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    } else {
        AnimationSplashContent(
            scaleAnimation = scaleAnimation,
            durationMillisAnimation = 1000,
            delayScreen = 100L
        ) {
            navController.navigate("auth") {
                popUpTo(route = Screen.SplashScreen.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.tertiary)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(R.drawable.unerg),
                contentDescription = stringResource(R.string.app_name),
                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .scale(scale = scaleAnimation.value),
            )
            Text(
                "Universidad Nacional Experimental de los Llanos Centrales Rómulo Gallegos.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .scale(scale = scaleAnimation.value)
            )
        }


}
