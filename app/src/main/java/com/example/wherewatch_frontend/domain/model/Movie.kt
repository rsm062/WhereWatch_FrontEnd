package com.example.wherewatch_frontend.domain.model

data class Movie(val id: Long,
                 val title: String,
                 val overview: String?,
                 val releaseDate: String?,
                 val availabilities: List<Availability>
)
