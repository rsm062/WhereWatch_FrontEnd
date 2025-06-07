package com.example.wherewatch_frontend.data.mapper

import com.example.wherewatch_frontend.data.dto.PlatformDTO
import com.example.wherewatch_frontend.domain.model.Platform

/**
 * Maps a [PlatformDTO] object from the data layer to a [Platform] domain model.
 *
 * @return A [Platform] object containing the mapped platform data.
 */
fun PlatformDTO.toDomain(): Platform {
    return Platform(
        id = id,
        name = name,
        logoPath = logoPath
    )
}