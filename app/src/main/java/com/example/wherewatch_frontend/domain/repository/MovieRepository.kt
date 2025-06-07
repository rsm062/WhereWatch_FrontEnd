package com.example.wherewatch_frontend.domain.repository

import com.example.wherewatch_frontend.domain.model.Movie

/**
 * Repository interface that defines the contract for accessing movie-related data in the domain layer.
 *
 * Implementations of this interface are responsible for providing movie data,
 * typically from remote APIs, local databases, or a combination of both.
 */
interface MovieRepository {

    /**
     * Searches for a list of movies that match the given title.
     *
     * @param title The title (or partial title) to search for.
     * @return A list of [Movie] objects that match the search query.
     */
    suspend fun searchMoviesByTitle(title: String): List<Movie>
    /**
     * Retrieves a single movie by its unique identifier.
     *
     * @param id The unique ID of the movie.
     * @return A [Movie] object containing detailed information.
     */
    suspend fun searchMovieById(id: Int): Movie

}