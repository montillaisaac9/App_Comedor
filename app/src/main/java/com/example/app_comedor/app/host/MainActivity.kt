package com.example.app_comedor.app.host

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.app_comedor.presentacion.navegation.graph.RootNavigationGraph
import com.example.app_comedor.presentacion.theme.AppComedorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppComedorTheme {
                RootNavigationGraph()
            }
        }
    }
}

