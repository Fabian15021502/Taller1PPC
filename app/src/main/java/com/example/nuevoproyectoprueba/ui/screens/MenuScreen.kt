package com.example.nuevoproyectoprueba.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MenuScreen(navController: NavController) {
    var selectedMode by remember { mutableStateOf("individual") }
    var numberOfTeamsInput by remember { mutableStateOf("") } // Captura el input del usuario
    var numberOfRoundsInput by remember { mutableStateOf("") } // Captura el input del usuario

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Juego de Palabras",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Selector de modo
        Text("Modo de Juego:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterChip(
                selected = selectedMode == "individual",
                onClick = { selectedMode = "individual" },
                label = { Text("Individual") }
            )
            FilterChip(
                selected = selectedMode == "teams",
                onClick = { selectedMode = "teams" },
                label = { Text("Equipos") }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Configuración de equipos (solo si modo equipos)
        if (selectedMode == "teams") {
            OutlinedTextField(
                value = numberOfTeamsInput,
                onValueChange = {
                    // Solo permitir números
                    if (it.isEmpty() || (it.toIntOrNull() != null && it.toInt() in 2..10)) {
                        numberOfTeamsInput = it
                    }
                },
                label = { Text("Número de Equipos (2-10)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ej: 3") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = numberOfRoundsInput,
                onValueChange = {
                    // Solo permitir números
                    if (it.isEmpty() || (it.toIntOrNull() != null && it.toInt() in 1..10)) {
                        numberOfRoundsInput = it
                    }
                },
                label = { Text("Número de Rondas (1-10)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ej: 3") }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // Obtener valores reales capturados del usuario
                val teams = if (selectedMode == "individual") {
                    1
                } else {
                    // Convertir el input a Int, debe existir porque el botón está habilitado
                    val inputValue = numberOfTeamsInput.toIntOrNull() ?: 2
                    inputValue.coerceIn(2, 10)
                }

                val rounds = if (selectedMode == "individual") {
                    1
                } else {
                    // Convertir el input a Int, debe existir porque el botón está habilitado
                    val inputValue = numberOfRoundsInput.toIntOrNull() ?: 3
                    inputValue.coerceIn(1, 10)
                }

                // Debug completo
                println("=== DEBUG MENUSCREEN ===")
                println("Mode: $selectedMode")
                println("Input Equipos: '$numberOfTeamsInput'")
                println("Input Rondas: '$numberOfRoundsInput'")
                println("Teams calculados: $teams")
                println("Rounds calculados: $rounds")
                println("Navegando a: categories/$selectedMode/$teams/$rounds")
                println("========================")

                navController.navigate("categories/$selectedMode/$teams/$rounds")
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = if (selectedMode == "teams") {
                numberOfTeamsInput.isNotEmpty() &&
                        numberOfRoundsInput.isNotEmpty() &&
                        numberOfTeamsInput.toIntOrNull() != null &&
                        numberOfRoundsInput.toIntOrNull() != null
            } else {
                true
            }
        ) {
            Text("Comenzar")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { /* acción para salir */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salir")
        }
    }
}
