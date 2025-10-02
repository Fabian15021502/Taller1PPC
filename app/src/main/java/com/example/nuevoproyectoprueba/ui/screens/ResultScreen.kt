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

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.navigate("menu") }, modifier = Modifier.fillMaxWidth()) {
            Text("Reiniciar")
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