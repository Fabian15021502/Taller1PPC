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
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(
            onClick = { navController.navigate("categories/individual") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Juego Individual")
        }
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
