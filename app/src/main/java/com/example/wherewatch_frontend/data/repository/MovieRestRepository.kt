package com.example.wherewatch_frontend.data.repository

import com.example.wherewatch_frontend.data.source.remote.apis.MovieApi
import com.example.wherewatch_frontend.domain.model.Movie
import com.example.wherewatch_frontend.domain.repository.MovieRepository
import com.example.wherewatch_frontend.data.mapper.toDomain


class MovieRestRepository(
    private val api: MovieApi
) : MovieRepository {

    override suspend fun searchMoviesByTitle(title: String): List<Movie> {
        val response = api.searchMoviesByTitle(title)
        return response.map { it.toDomain() }
    }
}