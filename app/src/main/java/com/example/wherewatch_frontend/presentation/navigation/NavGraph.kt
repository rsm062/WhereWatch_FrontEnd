package com.example.wherewatch_frontend.presentation.navigation

import MovieDetailsViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wherewatch_frontend.presentation.ui.screens.MovieDetailScreen
import com.example.wherewatch_frontend.presentation.ui.screens.SearchScreen
import com.example.wherewatch_frontend.presentation.viewmodel.SearchViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun NavGraph(startDestination: String = Screens.SearchScreen.route) {
    val navController = rememberNavController()
    val searchViewModel : SearchViewModel = koinViewModel()
    val movieDetailsViewModel : MovieDetailsViewModel = koinViewModel()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screens.SearchScreen.route) {
            SearchScreen(navController, searchViewModel)
        }
        composable(Screens.MovieDetailsScreen.route) {backStackEntry ->
            val title = backStackEntry.arguments?.getString("title")
            if (title != null) {
                MovieDetailScreen(navController, title, movieDetailsViewModel)
            }
        }

    }
}
