package com.example.nuevoproyectoprueba.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MenuScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(
            onClick = { navController.navigate("categories/individual") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Jugar")
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
