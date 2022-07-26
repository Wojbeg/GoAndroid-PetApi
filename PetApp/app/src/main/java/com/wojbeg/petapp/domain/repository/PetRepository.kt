package com.wojbeg.petapp.domain.repository

import com.wojbeg.petapp.common.Resource
import com.wojbeg.petapp.data.remote.dto.MessageResponseDto
import com.wojbeg.petapp.domain.model.Pet

interface PetRepository {

    suspend fun getPets(): Resource<List<Pet>>

    suspend fun getPetById(petId: Int): Resource<Pet>

    suspend fun createPet(pet: Pet): Resource<MessageResponseDto>

    suspend fun updatePet(pet: Pet): Resource<MessageResponseDto>

    suspend fun deletePet(petId: Int): Resource<MessageResponseDto>

}