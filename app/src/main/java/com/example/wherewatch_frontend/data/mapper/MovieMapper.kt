package com.example.wherewatch_frontend.data.mapper

import com.example.wherewatch_frontend.data.dto.MovieDTO
import com.example.wherewatch_frontend.domain.model.Movie

/**
 * Maps a [MovieDTO] object from the data layer to a [Movie] domain model.
 *
 * This includes mapping of nested [AvailabilityDTO] objects to domain [Availability] models.
 *
 * @return A [Movie] object containing full movie data and availability information.
 */
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