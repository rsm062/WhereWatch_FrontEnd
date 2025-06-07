package com.example.wherewatch_frontend.data.source.remote.apis

import com.example.wherewatch_frontend.data.dto.MovieDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit interface that defines endpoints for accessing movie data from a remote API.
 *
 * This interface uses Kotlin coroutines (suspend functions) for asynchronous network calls.
 */
interface MovieApi {

    /**
     * Searches for movies by their title.
     *
     * @param title The title (or partial title) of the movie to search for.
     * @return A list of [MovieDTO] objects matching the given title.
     */
    @GET("/movies/search")
    suspend fun searchMoviesByTitle(
        @Query("title") title: String
    ): List<MovieDTO>

    /**
     * Retrieves detailed information about a specific movie by its ID.
     *
     * @param id The unique identifier of the movie.
     * @return A [MovieDTO] object containing the movie's details.
     */
    @GET("/movies/{id}")
    suspend fun searchMovieById(
        @Path("id") id: Int
    ): MovieDTO

}