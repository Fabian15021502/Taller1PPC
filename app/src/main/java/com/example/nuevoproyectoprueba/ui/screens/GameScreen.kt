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
fun GameScreen(
    navController: NavController,
    category: String,
    mode: String,
    totalTeams: Int,
    totalRounds: Int
) {
    // Debug: Verificar valores recibidos
    LaunchedEffect(Unit) {
        println("GameScreen - totalTeams: $totalTeams, totalRounds: $totalRounds, mode: $mode")
    }

    // Inicialización de palabras
    val initialWords = remember { WordProvider.getWords(category) }

    // Estados del juego
    var palabrasDisponibles by remember { mutableStateOf(initialWords.shuffled()) }
    var tiempoRestante by remember { mutableIntStateOf(30) }
    var palabraActual by remember { mutableStateOf(palabrasDisponibles.firstOrNull() ?: "") }
    var scores by remember { mutableStateOf(List(totalTeams) { 0 }) }
    var currentTeamIndex by remember { mutableIntStateOf(0) }
    var currentRound by remember { mutableIntStateOf(1) }
    var isPlaying by remember { mutableStateOf(false) }
    var showTransition by remember { mutableStateOf(true) }

    // Función para reiniciar las palabras
    fun resetWords() {
        palabrasDisponibles = initialWords.shuffled()
        palabraActual = palabrasDisponibles.firstOrNull() ?: ""
    }

    // Función para finalizar turno
    fun endTurn() {
        println("endTurn - Equipo actual: ${currentTeamIndex + 1}, Ronda: $currentRound")
        isPlaying = false

        if (mode == "teams") {
            // Incrementar el índice del equipo
            val nextTeamIndex = currentTeamIndex + 1

            if (nextTeamIndex < totalTeams) {
                // Hay más equipos que deben jugar en esta ronda
                currentTeamIndex = nextTeamIndex
                showTransition = true
                println("Siguiente equipo: ${currentTeamIndex + 1}")
            } else {
                // Todos los equipos jugaron esta ronda
                if (currentRound < totalRounds) {
                    // Pasar a la siguiente ronda
                    currentRound++
                    currentTeamIndex = 0
                    showTransition = true
                    println("Nueva ronda: $currentRound, vuelve Equipo 1")
                } else {
                    // Juego terminado
                    println("Juego terminado - Mostrando resultados")
                    val scoresJson = Gson().toJson(scores.toIntArray())
                    navController.navigate("results/$scoresJson/$mode") {
                        popUpTo("menu") { inclusive = false }
                    }
                }
            }
        } else {
            // Modo individual
            val scoresJson = Gson().toJson(scores.toIntArray())
            navController.navigate("results/$scoresJson/$mode") {
                popUpTo("menu") { inclusive = false }
            }
        }
    }

    // Función para iniciar turno
    fun startTurn() {
        resetWords()
        tiempoRestante = 30
        isPlaying = true
        showTransition = false
        println("Turno iniciado - Equipo ${currentTeamIndex + 1}, Ronda $currentRound")
    }

    // Función para pasar a la siguiente palabra
    fun nextWord(correct: Boolean) {
        if (!isPlaying) return

        if (correct) {
            val newScores = scores.toMutableList()
            newScores[currentTeamIndex]++
            scores = newScores
            println("Punto para Equipo ${currentTeamIndex + 1}. Score: ${newScores[currentTeamIndex]}")
        }

        if (palabrasDisponibles.size > 1) {
            palabrasDisponibles = palabrasDisponibles.drop(1)
            palabraActual = palabrasDisponibles.first()
        } else {
            endTurn()
        }
    }

    // Timer
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            while (tiempoRestante > 0 && isPlaying) {
                delay(1000L)
                tiempoRestante--
            }
            if (isPlaying && tiempoRestante == 0) {
                endTurn()
            }
        }
    }

    // UI
    if (showTransition && mode == "teams") {
        // Pantalla de transición entre turnos
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Ronda $currentRound de $totalRounds",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Turno del Equipo ${currentTeamIndex + 1}",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Total de Equipos: $totalTeams",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Puntuaciones Actuales:",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    scores.forEachIndexed { index, score ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Equipo ${index + 1}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = if (index == currentTeamIndex) FontWeight.Bold else FontWeight.Normal,
                                color = if (index == currentTeamIndex)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                "$score puntos",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = if (index == currentTeamIndex) FontWeight.Bold else FontWeight.Normal,
                                color = if (index == currentTeamIndex)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { startTurn() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("¡Comenzar Turno!", style = MaterialTheme.typography.titleLarge)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = {
                    val scoresJson = Gson().toJson(scores.toIntArray())
                    navController.navigate("results/$scoresJson/$mode") {
                        popUpTo("menu") { inclusive = false }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Terminar Juego Ahora")
            }
        }
    } else {
        // Pantalla de juego activa
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Categoría: $category",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "⏱ $tiempoRestante s",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = if (tiempoRestante <= 10)
                                MaterialTheme.colorScheme.error
                            else
                                MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (mode == "teams") {
                        Text(
                            "Ronda: $currentRound / $totalRounds",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            "Turno del Equipo ${currentTeamIndex + 1} de $totalTeams",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            scores.forEachIndexed { index, score ->
                                Text(
                                    "E${index + 1}: $score",
                                    fontWeight = if (index == currentTeamIndex)
                                        FontWeight.Bold
                                    else
                                        FontWeight.Normal,
                                    color = if (index == currentTeamIndex)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    } else {
                        Text(
                            "Puntuación: ${scores[0]}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de inicio (solo para modo individual en primera vez)
            if (!isPlaying && mode == "individual" && showTransition) {
                Button(
                    onClick = { startTurn() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("¡Comenzar Juego!", style = MaterialTheme.typography.titleLarge)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Card con palabra
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isPlaying) palabraActual else if (mode == "individual") "Presiona ¡Comenzar Juego!" else "Presiona ¡Comenzar Turno!",
                        style = MaterialTheme.typography.displayMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }) {
            Text("Pasar")
        }
    }
}