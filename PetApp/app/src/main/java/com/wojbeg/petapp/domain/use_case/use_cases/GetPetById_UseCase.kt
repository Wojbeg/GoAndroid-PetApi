package com.wojbeg.petapp.domain.use_case.use_cases

import com.wojbeg.petapp.common.Resource
import com.wojbeg.petapp.domain.model.Pet
import com.wojbeg.petapp.domain.repository.PetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPetById_UseCase @Inject constructor(
    private val repository: PetRepository
) {
    operator fun invoke(petId: Int): Flow<Resource<Pet>> = flow {
        emit(Resource.Loading<Pet>())
        emit(repository.getPetById(petId))
    }.flowOn(Dispatchers.IO)
}