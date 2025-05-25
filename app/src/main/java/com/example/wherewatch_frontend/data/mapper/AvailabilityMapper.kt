package com.example.wherewatch_frontend.data.mapper

import com.example.wherewatch_frontend.data.dto.AvailabilityDTO
import com.example.wherewatch_frontend.domain.model.Availability

fun AvailabilityDTO.toDomain(): Availability {
    return Availability(
        platform = platform.toDomain(),
        country = country.toDomain()
    )
}