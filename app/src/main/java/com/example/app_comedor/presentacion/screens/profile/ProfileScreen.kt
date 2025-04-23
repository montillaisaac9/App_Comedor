package com.example.app_comedor.presentacion.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.app_comedor.data.network.models.auth.toDTO
import com.example.app_comedor.presentacion.common.progresBar.CustomProgressBar
import com.example.app_comedor.presentacion.common.topBar.CustomTopBar
import com.example.app_comedor.presentacion.navegation.destination.Screen
import com.example.app_comedor.presentacion.screens.profile.components.UserInfoField
import com.example.app_comedor.utils.HOST
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = koinViewModel()) {

    val dbUser = viewModel.responseDataBase

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Profile",
            ) {
                navController.popBackStack()
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (dbUser?.name != "") {

                val user = dbUser?.toDTO()

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    if (user?.photo != "") {
                        AsyncImage(
                            model = user?.photo?.replace(
                                "http://localhost:3000/",
                                HOST
                            ),
                            contentDescription = "Imagen de Perfil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Subir foto",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // User Data Section Title
                Text(
                    text = "Datos de usuario",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                // User Info Fields
                UserInfoField(label = "Nombre:", value = user?.name?: "")

                LazyColumn (modifier = Modifier.fillMaxWidth()){
                    items (user?.careers?.size ?: 0){ index ->
                        UserInfoField(label = "Carrera:", value = user?.careers[index]?.name?: "")
                    }
                }

                UserInfoField(label = "Celular:", value = user?.identification?: "")
                UserInfoField(label = "E-mail:", value = user?.email?: "")

                Spacer(modifier = Modifier.weight(1f))

                // Edit Profile Button
                Button(
                    onClick = {
                        navController.navigate(Screen.SplashScreen.route) {
                            launchSingleTop = true
                        }
                        viewModel.closeSession()
                    },
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFAE7B9),
                        contentColor = Color(0xFF8B4513)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Cerrar Sessiop",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            } else CustomProgressBar()
        }
    }
}