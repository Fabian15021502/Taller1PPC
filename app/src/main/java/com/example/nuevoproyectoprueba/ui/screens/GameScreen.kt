package com.example.nuevoproyectoprueba.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nuevoproyectoprueba.model.WordProvider
import kotlinx.coroutines.delay

@Composable
fun GameScreen(navController: NavController, category: String, mode: String) {
    var scoreA by remember { mutableStateOf(0) }
    var scoreB by remember { mutableStateOf(0) }
    var currentTeam by remember { mutableStateOf("A") } // alterna entre equipos

    var timeLeft by remember { mutableStateOf(30) } // 30 segundos por ronda
    val words = remember { WordProvider.getWords(category).shuffled().toMutableList() }
    var word by remember { mutableStateOf(words.removeAt(0) ?:"Fin") }

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
        Text("Categoría: $category", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        Text("Tiempo: $timeLeft s", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Palabra: $word", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        if (mode == "individual") {
            Text("Puntaje: $scoreA", style = MaterialTheme.typography.bodyLarge)
        } else {
            Text("Equipo A: $scoreA", style = MaterialTheme.typography.bodyLarge)
            Text("Equipo B: $scoreB", style = MaterialTheme.typography.bodyLarge)
            Text("Turno: Equipo $currentTeam", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            if (mode == "individual") {
                scoreA++
            } else {
                if (currentTeam == "A") scoreA++ else scoreB++
                // alterna equipo
                currentTeam = if (currentTeam == "A") "B" else "A"
            }

            // Corrige el error usando removeAt(0)
            word = if (words.isNotEmpty()) words.removeAt(0) else "Fin"
            if (word == "Fin") {
                navController.navigate("results/$scoreA/$scoreB/$mode")
            }
        }) {
            Text("Correcto (+1)")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            // Corrige el error usando removeAt(0)
            word = if (words.isNotEmpty()) words.removeAt(0) else "Fin"
            if (word == "Fin") {
                navController.navigate("results/$scoreA/$scoreB/$mode")
                return@Button
            }
            if (mode == "equipos") {
                // alterna equipo en modo equipos
                currentTeam = if (currentTeam == "A") "B" else "A"
            }
        }) {
            Text("Pasar")
        }
    }
}
