package com.example.nuevoproyectoprueba.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun GameScreen(navController: NavController) {
    var score by remember { mutableStateOf(0) }
    var timeLeft by remember { mutableStateOf(60) } // 60 segundos
    var word by remember { mutableStateOf("Perro") } // temporal, luego usamos WordProvider

    // Temporizador
    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000)
            timeLeft--
        }
        navController.navigate("results/$score")
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Tiempo: $timeLeft s", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Palabra: $word", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Puntaje: $score", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            score++
            word = "Nueva palabra"
        }) {
            Text("Correcto (+1)")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { word = "Otra palabra" }) {
            Text("Pasar")
        }
    }
}
