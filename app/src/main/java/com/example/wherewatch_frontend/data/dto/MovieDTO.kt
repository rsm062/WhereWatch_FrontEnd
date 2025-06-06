package com.example.wherewatch_frontend.data.dto

data class MovieDTO(val id: Int,
                    val title: String,
                    val overview: String?,
                    val releaseDate: String?,
                    val posterPath: String?,
                    val rating: Double?,
                    val availabilities: List<AvailabilityDTO>
)
