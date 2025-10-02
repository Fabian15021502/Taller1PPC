package com.example.nuevoproyectoprueba.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(
            "🏆 Resultados Finales",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (winners.size > 1) {
            Text(
                "¡Empate!",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            val winnerText = if (modeFinal == "individual") {
                "¡Juego Terminado!"
            } else {
                "¡Ganador: Equipo ${winners.first().first}!"
            }
            Text(
                winnerText,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                scoresWithIndex.forEachIndexed { position, (teamIndex, score) ->
                    val isWinner = score == winnerScore
                    val medal = when (position) {
                        0 -> "🥇"
                        1 -> "🥈"
                        2 -> "🥉"
                        else -> "${position + 1}."
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "$medal ${if (modeFinal == "individual") "Jugador" else "Equipo $teamIndex"}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = if (isWinner) FontWeight.Bold else FontWeight.Normal,
                            color = if (isWinner) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            "$score puntos",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = if (isWinner) FontWeight.Bold else FontWeight.Normal,
                            color = if (isWinner) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    if (position < scoresWithIndex.size - 1) {
                        HorizontalDivider()
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("menu") {
                popUpTo("menu") { inclusive = true }
            }},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al Menú Principal", style = MaterialTheme.typography.titleMedium)
        }
    }
}