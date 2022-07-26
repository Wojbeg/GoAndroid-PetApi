package com.wojbeg.petapp.data.remote.dto

import com.google.gson.annotations.Expose

data class PetDto(
    @Expose(serialize = false, deserialize = true)
    val id: Int,
    val age: Int,
    val description: String,
    val food: String,
    val name: String,
    val photo: String,
    val species: String
)