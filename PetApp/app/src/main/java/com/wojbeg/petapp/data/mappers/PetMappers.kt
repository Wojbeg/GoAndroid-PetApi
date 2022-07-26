package com.wojbeg.petapp.data.mappers

import com.wojbeg.petapp.data.remote.dto.PetDto
import com.wojbeg.petapp.domain.model.Pet

fun PetDto.toPet(): Pet {
    return Pet(
        id = id,
        age = age,
        description = description,
        food = food,
        name = name,
        photo = photo,
        species = species
    )
}

fun Pet.toPetDto(): PetDto {
    return PetDto(
        id = id,
        age = age,
        description = description,
        food = food,
        name = name,
        photo = photo,
        species = species
    )
}