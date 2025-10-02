package com.example.nuevoproyectoprueba.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CategoryScreen(navController: NavController, mode: String) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
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

        Button(
            onClick = { navController.navigate("game/Profesiones/$mode") },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Profesiones") }
    }
}
