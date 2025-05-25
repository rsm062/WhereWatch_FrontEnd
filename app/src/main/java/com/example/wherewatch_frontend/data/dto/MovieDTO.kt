package com.example.wherewatch_frontend.data.dto

data class MovieDTO(val id: Long,
                    val title: String,
                    val overview: String?,
                    val releaseDate: String?,
                    val availabilities: List<AvailabilityDTO>
)
