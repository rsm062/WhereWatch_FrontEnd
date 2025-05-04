package com.example.wherewatch_frontend.data.model

data class Movie(val id: Long,
                 val title: String,
                 val overview: String?,
                 val releaseDate: String?,
                 val platforms: List<Platform> = emptyList(),
                 val countries: List<Country> = emptyList()
)
