package com.example.nuevoproyectoprueba

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.nuevoproyectoprueba.ui.screens.CategoryScreen
import com.example.nuevoproyectoprueba.ui.screens.GameScreen
import com.example.nuevoproyectoprueba.ui.screens.MenuScreen
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

        composable(
            route = "game/{category}/{mode}/{teams}/{rounds}",
            arguments = listOf(
                navArgument("category") {
                    type = NavType.StringType
                    defaultValue = "Animales"
                },
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
            val category = backStackEntry.arguments?.getString("category") ?: "Animales"
            val mode = backStackEntry.arguments?.getString("mode") ?: "individual"
            val teams = backStackEntry.arguments?.getInt("teams") ?: 1
            val rounds = backStackEntry.arguments?.getInt("rounds") ?: 3

            // Debug
            println("=== DEBUG GAMESCREEN ===")
            println("Category: $category")
            println("Mode recibido: $mode")
            println("Teams recibido: $teams")
            println("Rounds recibido: $rounds")
            println("========================")

            GameScreen(
                navController = navController,
                category = category,
                mode = mode,
                totalTeams = teams,
                totalRounds = rounds
            )
        }

        composable(
            route = "results/{scoresJson}/{mode}",
            arguments = listOf(
                navArgument("scoresJson") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("mode") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val scoresJson = backStackEntry.arguments?.getString("scoresJson")
            val mode = backStackEntry.arguments?.getString("mode")

            ResultScreen(
                navController = navController,
                scoresJson = scoresJson,
                mode = mode
            )
        }
    }
}