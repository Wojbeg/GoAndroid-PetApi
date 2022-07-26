package com.wojbeg.petapp.presentation.pet_list.components

import com.wojbeg.petapp.domain.model.Pet

data class PetsState (
    val isLoading: Boolean = false,
    val pets: List<Pet> = emptyList(),
    val error: String = "",

    val isDialogOpen: Boolean = false,
    val idPreparedToDelete: Int = -1
)