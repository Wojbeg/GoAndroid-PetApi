package com.wojbeg.petapp.domain.use_case

import com.wojbeg.petapp.domain.use_case.use_cases.CreatePet_UseCase
import com.wojbeg.petapp.domain.use_case.use_cases.UpdatePet_UseCase

data class DetailsUseCases(
    val createPet: CreatePet_UseCase,
    val updatePet: UpdatePet_UseCase,
)
