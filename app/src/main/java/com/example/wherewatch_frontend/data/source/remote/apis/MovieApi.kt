package com.example.wherewatch_frontend.data.source.remote.apis

import com.example.wherewatch_frontend.data.dto.MovieDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("/movies/search")
    suspend fun searchMoviesByTitle(
        @Query("title") title: String
    ): List<MovieDTO>

}