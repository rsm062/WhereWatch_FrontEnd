package com.example.wherewatch_frontend.data.mapper

import com.example.wherewatch_frontend.data.dto.PlatformDTO
import com.example.wherewatch_frontend.domain.model.Platform

fun PlatformDTO.toDomain(): Platform {
    return Platform(
        id = id,
        name = name,
        logoUrl = logoUrl
    )
}