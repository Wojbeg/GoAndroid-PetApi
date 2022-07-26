package com.wojbeg.petapp.data.repository

import com.wojbeg.petapp.common.Resource
import com.wojbeg.petapp.data.mappers.toPet
import com.wojbeg.petapp.data.mappers.toPetDto
import com.wojbeg.petapp.data.remote.PetApi
import com.wojbeg.petapp.data.remote.dto.MessageResponseDto
import com.wojbeg.petapp.domain.model.Pet
import com.wojbeg.petapp.domain.repository.PetRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PetRepositoryImpl @Inject constructor(
    private val api:PetApi
): PetRepository {

    private fun <T> handleException(exception: Exception): Resource.Error<T> {
        return when (exception) {
            is HttpException -> Resource.Error<T>(
                message = exception.localizedMessage ?: "An unknown error occurred."
            )

            is IOException -> Resource.Error<T>(
                message = "Couldn't reach server. Check your internet connection."
            )

            else -> Resource.Error<T>(
                message = exception.message ?: "An unknown error occurred."
            )
        }
    }

    override suspend fun getPets(): Resource<List<Pet>> {

        return try {
            val response = api.getPets()

            if (response.isSuccessful) {
                Resource.Success(
                    data = response.body()!!.petList.map {
                        it.toPet()
                    }
                )
            } else {

                Resource.Error(
                    message = response.errorBody().toString()
                )
            }
        } catch (e: Exception) {
            handleException(e)
        }

    }

    override suspend fun getPetById(petId: Int): Resource<Pet> {

        return try {
            val response = api.getPetById(petId)

            if (response.isSuccessful) {
                Resource.Success(
                    data = response.body()!!.pet.toPet()
                )
            } else {

                Resource.Error(
                    message = response.errorBody().toString(),
                    code = response.code()
                )
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun createPet(pet: Pet): Resource<MessageResponseDto> {

        return try {
            val response = api.createPet(pet.toPetDto())

            if (response.isSuccessful) {
                Resource.Success(
                    data = response.body()!!
                )
            } else {
                Resource.Error(
                    message = response.errorBody().toString(),
                    code = response.code()
                )
            }

        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun updatePet(pet: Pet): Resource<MessageResponseDto> {
        return try {
            val response = api.updatePet(pet.id, pet.toPetDto())

            if (response.isSuccessful) {
                Resource.Success(
                    data = response.body()!!
                )
            } else {
                Resource.Error(
                    message = response.errorBody().toString(),
                    code = response.code()
                )
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun deletePet(petId: Int): Resource<MessageResponseDto> {
        return try {
            val response = api.deletePed(petId)

            if (response.isSuccessful) {
                Resource.Success(
                    data = response.body()!!
                )
            } else {
                Resource.Error(
                    message = response.errorBody().toString(),
                    code = response.code()
                )
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }
}