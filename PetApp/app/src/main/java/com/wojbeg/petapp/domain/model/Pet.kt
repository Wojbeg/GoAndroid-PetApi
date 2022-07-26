package com.wojbeg.petapp.domain.model

data class Pet(
    val id: Int,
    var age: Int,
    var description: String,
    var food: String,
    var name: String,
    var photo: String,
    var species: String
)