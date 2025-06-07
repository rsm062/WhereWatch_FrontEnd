package com.example.wherewatch_frontend.data.repository

import com.example.wherewatch_frontend.data.source.remote.apis.MovieApi
import com.example.wherewatch_frontend.domain.model.Movie
import com.example.wherewatch_frontend.domain.repository.MovieRepository
import com.example.wherewatch_frontend.data.mapper.toDomain

/**
 * Implementation of [MovieRepository] that retrieves movie data from a remote REST API.
 *
 * This repository uses [MovieApi] to perform network requests and maps the response DTOs
 * to domain models using the `toDomain` extension function.
 *
 * @property api The Retrofit API interface used to fetch movie data.
 */
class MovieRestRepository(
    private val api: MovieApi
) : MovieRepository {

    /**
     * Searches for movies by title from the remote API and maps the results to domain models.
     *
     * @param title The movie title or partial title to search for.
     * @return A list of [Movie] domain objects matching the search criteria.
     */
    override suspend fun searchMoviesByTitle(title: String): List<Movie> {
        val response = api.searchMoviesByTitle(title)
        return response.map { it.toDomain() }
    }

    /**
     * Retrieves a movie by its ID from the remote API and maps it to a domain model.
     *
     * @param id The unique identifier of the movie.
     * @return A [Movie] domain object with detailed information.
     */
    override suspend fun searchMovieById(id: Int): Movie {
        val response = api.searchMovieById(id)
        return response.toDomain()
    }
}