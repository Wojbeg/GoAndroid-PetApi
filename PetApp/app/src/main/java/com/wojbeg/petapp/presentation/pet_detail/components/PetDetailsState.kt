package com.wojbeg.petapp.presentation.pet_detail.components

import com.wojbeg.petapp.domain.model.Pet

data class PetDetailsState(
    var pet: Pet = Pet(-1,0, "", "", "", "", ""),

    val photoUrl: String = "",
    val age: String = pet.age.toString(),

    val nameError: Boolean = false,
    val ageError: Boolean = false,
    val isLoading: Boolean = false,
)
