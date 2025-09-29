package com.example.nuevoproyectoprueba.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun GameScreen(navController: NavController, category: String, mode: String) {
    var scoreA by remember { mutableStateOf(0) }
    var scoreB by remember { mutableStateOf(0) }
    var currentTeam by remember { mutableStateOf("A") } // alterna entre equipos

    var timeLeft by remember { mutableStateOf(30) } // 30 segundos por ronda
    val words = remember { WordProvider.getWords(category).shuffled().toMutableList() }
    var word by remember { mutableStateOf(words.removeFirstOrNull() ?: "Fin") }

    // Temporizador
    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000)
            timeLeft--
        }
        // Al terminar tiempo, pasa a resultados
        navController.navigate("results/$scoreA/$scoreB/$mode")
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
