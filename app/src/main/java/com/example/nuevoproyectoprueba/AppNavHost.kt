package com.example.nuevoproyectoprueba

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nuevoproyectoprueba.ui.screens.MenuScreen
import com.example.nuevoproyectoprueba.ui.screens.CategoryScreen
import com.example.nuevoproyectoprueba.ui.screens.GameScreen
import com.example.nuevoproyectoprueba.ui.screens.ResultScreen


@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "menu"
    ) {
        composable("menu") { MenuScreen(navController) }
        composable("categories") { CategoryScreen(navController) }
        composable("game") { GameScreen(navController) }
        composable("results/{finalScore}") { backStackEntry ->
            val score = backStackEntry.arguments?.getString("finalScore")?.toIntOrNull() ?: 0
            ResultScreen(navController, finalScore = score)
        }
    }
}
