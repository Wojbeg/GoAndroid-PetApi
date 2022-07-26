package com.wojbeg.petapp.presentation.pet_list.components

sealed class PetListEvent {
    object GetPets: PetListEvent()

    data class SetDialog(val value: Boolean = false, val idToDelete: Int = -1): PetListEvent()
    object DeletePet : PetListEvent()
}
