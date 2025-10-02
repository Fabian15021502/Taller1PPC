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
import com.example.nuevoproyectoprueba.model.WordProvider

@Composable
fun CategoryScreen(navController: NavController, mode: String, teams: Int, rounds: Int) {
    // Debug
    println("=== CategoryScreen ===")
    println("Mode: $mode, Teams: $teams, Rounds: $rounds")
    println("======================")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "👇 Selecciona una Categoría",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Mostrar configuración actual
        if (mode == "teams") {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Configuración:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text("• Equipos: $teams")
                    Text("• Rondas: $rounds")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        val categories = WordProvider.getAllCategories()

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(categories) { category ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                ) {
                    Button(
                        onClick = {
                            val route = "game/${category.name}/$mode/$teams/$rounds"
                            println("Navegando a: $route")
                            navController.navigate(route)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    ) {
                        Text(category.name, style = MaterialTheme.typography.titleLarge)
                    }
                }
            }
        }
    }
}