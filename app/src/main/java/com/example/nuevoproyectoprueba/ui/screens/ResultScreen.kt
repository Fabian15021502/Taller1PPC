package com.example.nuevoproyectoprueba.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ResultScreen(navController: NavController, finalScore: Int) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Puntaje final: $finalScore", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("menu") }, modifier = Modifier.fillMaxWidth()) {
            Text("Reiniciar")
        }
    }
}
