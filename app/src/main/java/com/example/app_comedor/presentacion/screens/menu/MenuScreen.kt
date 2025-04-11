package com.example.app_comedor.presentacion.screens.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app_comedor.presentacion.screens.menu.components.FoodMenuItem
import com.example.app_comedor.presentacion.theme.AppComedorTheme

@Composable
fun MenuScreen(navController: NavController) {

    val menu = listOf(
        MenuItem(),
        MenuItem(),
        MenuItem(),
        MenuItem()
    )

    Scaffold{ padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "App Comedor Estudiantil Unerg",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                modifier = Modifier.padding(top = 40.dp),
                text = "Menu Semanal",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items (items = menu){
                    FoodMenuItem(menuItem = it, modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

data class MenuItem(
    val url: String = "http://192.168.1.117:3000/uploads/images/dish/1742927917332-photo_2025-03-19_16-00-51.jpg",
    val title: String = "Arroz con pollo a la Unerg",
    val time: String = "14/04/2025 -  12:00 pm",
)

