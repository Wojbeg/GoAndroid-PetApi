package com.wojbeg.petapp.domain.use_case.use_cases

import com.wojbeg.petapp.common.Resource
import com.wojbeg.petapp.domain.model.Pet
import com.wojbeg.petapp.domain.repository.PetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPets_UseCase @Inject constructor(
    private val repository: PetRepository
) {
    operator fun invoke(): Flow<Resource<List<Pet>>> = flow {
        emit(Resource.Loading<List<Pet>>())
        emit(repository.getPets())
    }.flowOn(Dispatchers.IO)
}