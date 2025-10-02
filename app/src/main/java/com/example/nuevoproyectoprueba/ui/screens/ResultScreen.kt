package com.example.nuevoproyectoprueba.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ResultScreen(navController: NavController, scoresJson: String?, mode: String?) {
    val scores = remember(scoresJson) {
        if (scoresJson != null) {
            try {
                Gson().fromJson<IntArray>(scoresJson, object : TypeToken<IntArray>() {}.type).toList()
            } catch (e: Exception) {
                // Handle potential JSON parsing errors, e.g., log the error
                emptyList()
            }
        } else {
            emptyList()
        }
    }

    val modeFinal = remember(mode) {
        mode ?: "teams"
    }

    val scoresWithIndex = remember(scores) {
        scores.mapIndexed { index, score -> Pair(index + 1, score) }
            .sortedByDescending { it.second }
    }

    val winnerScore = scoresWithIndex.firstOrNull()?.second
    val winners = scoresWithIndex.filter { it.second == winnerScore }

        if (mode == "individual") {
            Text("Puntaje final: $scoreA", style = MaterialTheme.typography.headlineSmall)
        } else {
            Text("Equipo A: $scoreA puntos", style = MaterialTheme.typography.bodyLarge)
            Text("Equipo B: $scoreB puntos", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(16.dp))
            val ganador = when {
                scoreA > scoreB -> "Equipo A"
                scoreB > scoreA -> "Equipo B"
                else -> "Empate"
            }
            Text("Ganador: $ganador", style = MaterialTheme.typography.headlineSmall)
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.navigate("menu") }, modifier = Modifier.fillMaxWidth()) {
            Text("Reiniciar")
        }
    }
}
