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
fun AppNavHost(navController: NavHostController, startDestination: String = "menu") {
    NavHost(navController = navController, startDestination = startDestination) {

        composable("menu") {
            MenuScreen(navController = navController)
        }

        composable(
            route = "categories/{mode}/{teams}/{rounds}",
            arguments = listOf(
                navArgument("mode") {
                    type = NavType.StringType
                    defaultValue = "individual"
                },
                navArgument("teams") {
                    type = NavType.IntType
                    defaultValue = 1
                },
                navArgument("rounds") {
                    type = NavType.IntType
                    defaultValue = 3
                }
            )
        ) { backStackEntry ->
            val mode = backStackEntry.arguments?.getString("mode") ?: "individual"
            val teams = backStackEntry.arguments?.getInt("teams") ?: 1
            val rounds = backStackEntry.arguments?.getInt("rounds") ?: 3

            // Debug
            println("=== DEBUG CATEGORIES ===")
            println("Mode recibido: $mode")
            println("Teams recibido: $teams")
            println("Rounds recibido: $rounds")
            println("========================")

            CategoryScreen(
                navController = navController,
                mode = mode,
                teams = teams,
                rounds = rounds
            )
        }

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
