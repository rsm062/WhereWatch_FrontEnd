package com.example.wherewatch_frontend.data.mapper

import com.example.wherewatch_frontend.data.dto.MovieDTO
import com.example.wherewatch_frontend.domain.model.Movie

fun MovieDTO.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        posterPath = posterPath,
        rating = rating,
        availabilities = availabilities.map { it.toDomain() }
    )
}