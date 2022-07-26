package com.wojbeg.petapp.domain.use_case

import com.wojbeg.petapp.domain.use_case.use_cases.*

data class ListUseCases(
    val getPets: GetPets_UseCase,
    val getPetById: GetPetById_UseCase,
    val deletePet: DeletePet_UseCase,
)
