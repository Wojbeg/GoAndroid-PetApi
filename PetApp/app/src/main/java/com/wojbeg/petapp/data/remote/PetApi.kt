package com.wojbeg.petapp.data.remote

import com.wojbeg.petapp.data.remote.dto.MessageResponseDto
import com.wojbeg.petapp.data.remote.dto.PetDto
import com.wojbeg.petapp.data.remote.dto.ResponsePetDto
import com.wojbeg.petapp.data.remote.dto.ResponsePetListDto
import retrofit2.Response
import retrofit2.http.*

interface PetApi {

    @GET("pets")
    suspend fun getPets(): Response<ResponsePetListDto>

    @GET("pets/{petId}")
    suspend fun getPetById(@Path("petId") petId: Int): Response<ResponsePetDto>

    @POST("create_pet")
    suspend fun createPet(@Body pet: PetDto): Response<MessageResponseDto>

    @PUT("update_pet/{petId}")
    suspend fun updatePet(@Path("petId") petId: Int, @Body pet: PetDto): Response<MessageResponseDto>

    @DELETE("delete_pet/{petId}")
    suspend fun deletePed(@Path("petId") petId: Int): Response<MessageResponseDto>

}