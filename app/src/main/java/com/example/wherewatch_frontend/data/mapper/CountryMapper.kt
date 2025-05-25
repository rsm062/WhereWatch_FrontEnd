package com.example.wherewatch_frontend.data.mapper

import com.example.wherewatch_frontend.data.dto.CountryDTO
import com.example.wherewatch_frontend.domain.model.Country

fun CountryDTO.toDomain(): Country {
    return Country(
        id = id,
        name = name,
        isoCode = isoCode
    )
}