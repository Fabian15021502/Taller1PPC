package com.example.nuevoproyectoprueba.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CategoryScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Selecciona una categoría", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("game/Animales/$mode") },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Animales") }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("game/Películas/$mode") },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Películas") }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("game") }, modifier = Modifier.fillMaxWidth()) {
            Text("Profesiones")
        }
    }
}
