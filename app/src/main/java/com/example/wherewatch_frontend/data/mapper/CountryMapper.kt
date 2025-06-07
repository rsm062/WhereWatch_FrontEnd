package com.example.wherewatch_frontend.data.mapper

import com.example.wherewatch_frontend.data.dto.CountryDTO
import com.example.wherewatch_frontend.domain.model.Country

/**
 * Maps a [CountryDTO] object from the data layer to a [Country] domain model.
 *
 * @return A [Country] object containing the mapped country data.
 */
fun CountryDTO.toDomain(): Country {
    return Country(
        id = id,
        name = name,
        isoCode = isoCode
    )
}