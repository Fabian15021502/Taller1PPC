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
        Button(
            onClick = { navController.navigate("categories/equipos") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Juego en Equipos")
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
