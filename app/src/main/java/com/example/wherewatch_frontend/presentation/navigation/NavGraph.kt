package com.example.wherewatch_frontend.presentation.navigation

import android.net.Uri
import com.example.wherewatch_frontend.presentation.viewmodel.MovieDetailsViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wherewatch_frontend.presentation.ui.screens.MovieDetailScreen
import com.example.wherewatch_frontend.presentation.ui.screens.MovieSelectionScreen
import com.example.wherewatch_frontend.presentation.ui.screens.SearchScreen
import com.example.wherewatch_frontend.presentation.viewmodel.MovieSelectionViewModel
import com.example.wherewatch_frontend.presentation.viewmodel.SearchViewModel
import org.koin.androidx.compose.koinViewModel


/**
 * Composable function that sets up the navigation graph for the app using Jetpack Compose Navigation.
 *
 * It defines navigation routes for:
 * - Search screen
 * - Movie details screen with an integer argument "id"
 * - Movie selection screen with a string argument "title"
 *
 * ViewModels are injected using Koin's Compose integration.
 *
 * @param startDestination The initial route to start the navigation from. Defaults to the Search screen.
 */
@Composable
fun NavGraph(startDestination: String = Screens.SearchScreen.route) {
    val navController = rememberNavController()
    val searchViewModel : SearchViewModel = koinViewModel()
    val movieDetailsViewModel : MovieDetailsViewModel = koinViewModel()
    val movieSelectionViewModel : MovieSelectionViewModel = koinViewModel()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screens.SearchScreen.route) {
            SearchScreen(navController, searchViewModel)
        }
        composable(
            route = Screens.MovieDetailsScreen.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            MovieDetailScreen(navController, movieDetailsViewModel, id)
        }
        composable(Screens.MovieSelectionScreen.route) { backStackEntry ->
            val encodedTitle = backStackEntry.arguments?.getString("title")
            val title = encodedTitle?.let { Uri.decode(it) }
            if (title != null) {
                MovieSelectionScreen(navController, movieSelectionViewModel, title)
            }
        }

    }
}
