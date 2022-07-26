package com.wojbeg.petapp.presentation.pet_detail.components

sealed class PetDetailsEvent {
    data class EnterName(val value: String): PetDetailsEvent()
    data class EnterAge(val value: String): PetDetailsEvent()
    data class EnterSpecies(val value: String): PetDetailsEvent()
    data class EnterFood(val value: String): PetDetailsEvent()
    data class EnterDesc(val value: String): PetDetailsEvent()
    data class EnterPhoto(val value: String): PetDetailsEvent()
    data class SetPet(val pet: String): PetDetailsEvent()
    object SavePet: PetDetailsEvent()
}
