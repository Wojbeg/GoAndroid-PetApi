package com.wojbeg.petapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResponsePetListDto(
    @SerializedName("data")
    val petList: List<PetDto>,
    val message: String
)