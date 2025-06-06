package com.example.wherewatch_frontend.domain.repository

import com.example.wherewatch_frontend.domain.model.Movie

interface MovieRepository {
    suspend fun searchMoviesByTitle(title: String): List<Movie>
    suspend fun searchMovieById(id: Int): Movie

}