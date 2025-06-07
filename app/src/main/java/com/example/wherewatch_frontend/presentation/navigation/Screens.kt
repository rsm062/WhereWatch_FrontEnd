package com.example.wherewatch_frontend.presentation.navigation

/**
 * Sealed class representing the navigation routes (screens) within the app.
 *
 * Each object represents a screen route with an optional function to create
 * a properly formatted navigation route string, including route parameters.
 *
 * @property route The navigation route string.
 */
sealed class Screens(val route: String) {
    /**
     * Route for the Search screen.
     */
    object SearchScreen: Screens("search")

    /**
     * Route for the Movie Details screen, which expects an integer "id" parameter.
     *
     * @param id The movie ID to be passed in the navigation route.
     * @return The formatted route string including the movie ID.
     */
    object MovieDetailsScreen: Screens("MovieDetails/{id}"){
        fun createRoute(id: Int) = "MovieDetails/$id"
    }

    /**
     * Route for the Movie Selection screen, which expects a string "title" parameter.
     *
     * @param title The movie title to be passed in the navigation route.
     * @return The formatted route string including the movie title.
     */
    object MovieSelectionScreen: Screens("MovieSelection/{title}"){
        fun createRoute(title: String) = "MovieSelection/$title"
    }


}