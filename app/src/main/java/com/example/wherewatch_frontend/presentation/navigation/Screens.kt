package com.example.wherewatch_frontend.presentation.navigation

sealed class Screens(val route: String) {
    object SearchScreen: Screens("search")
    object MovieDetailsScreen: Screens("MovieDetails")
    object MovieSelectionScreen: Screens("MovieSelection/{title}"){
        fun createRoute(title: String) = "MovieSelection/$title"
    }


}