package com.example.wherewatch_frontend.presentation.navigation

sealed class Screens(val route: String) {
    object SearchScreen: Screens("search")
    object MovieDetailsScreen: Screens("MovieDetails")

}