package com.example.wherewatch_frontend.data.mapper

import com.example.wherewatch_frontend.data.dto.AvailabilityDTO
import com.example.wherewatch_frontend.domain.model.Availability

/**
 * Maps an [AvailabilityDTO] object to an [Availability] domain model.
 *
 * This includes nested mapping of [PlatformDTO] and [CountryDTO] to their respective domain models.
 *
 * @return An [Availability] object with platform and country data.
 */
fun AvailabilityDTO.toDomain(): Availability {
    return Availability(
        platform = platform.toDomain(),
        country = country.toDomain()
    )
}