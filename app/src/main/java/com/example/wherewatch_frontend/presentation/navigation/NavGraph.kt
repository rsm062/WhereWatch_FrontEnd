package com.example.wherewatch_frontend.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wherewatch_frontend.presentation.ui.screens.MovieDetailScreen
import com.example.wherewatch_frontend.presentation.ui.screens.SearchScreen



@Composable
fun NavGraph(startDestination: String = Screens.SearchScreen.route) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screens.SearchScreen.route) {
            SearchScreen()
        }
        composable(Screens.MovieDetailsScreen.route) {
            MovieDetailScreen()
        }

    }
}
