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

        composable("categories/{mode}") { backStackEntry ->
            val mode = backStackEntry.arguments?.getString("mode") ?: "individual"
            CategoryScreen(navController, mode)
        }

        composable("game/{category}/{mode}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            val mode = backStackEntry.arguments?.getString("mode") ?: "individual"
            GameScreen(navController, category, mode)
        }

        composable("results/{scoreA}/{scoreB}/{mode}") { backStackEntry ->
            val scoreA = backStackEntry.arguments?.getString("scoreA")?.toIntOrNull() ?: 0
            val scoreB = backStackEntry.arguments?.getString("scoreB")?.toIntOrNull() ?: 0
            val mode = backStackEntry.arguments?.getString("mode") ?: "individual"
            ResultScreen(navController, scoreA, scoreB, mode)
        }
    }
}
