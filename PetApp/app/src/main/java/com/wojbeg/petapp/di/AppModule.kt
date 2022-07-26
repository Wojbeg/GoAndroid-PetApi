package com.wojbeg.petapp.di

import com.wojbeg.petapp.common.Constants
import com.wojbeg.petapp.data.remote.PetApi
import com.wojbeg.petapp.data.repository.PetRepositoryImpl
import com.wojbeg.petapp.domain.repository.PetRepository
import com.wojbeg.petapp.domain.use_case.DetailsUseCases
import com.wojbeg.petapp.domain.use_case.ListUseCases
import com.wojbeg.petapp.domain.use_case.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun ProvidePetApi(): PetApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_PET)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(PetApi::class.java)
    }

    @Provides
    @Singleton
    fun ProvidePetRepository(api: PetApi): PetRepository {
        return PetRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideDetailUseCases(repository: PetRepository): DetailsUseCases {
        return DetailsUseCases(
            createPet = CreatePet_UseCase(repository),
            updatePet = UpdatePet_UseCase(repository),
        )
    }

    @Provides
    @Singleton
    fun provideListUseCases(repository: PetRepository): ListUseCases {
        return ListUseCases(
            getPets = GetPets_UseCase(repository),
            getPetById = GetPetById_UseCase(repository),
            deletePet = DeletePet_UseCase(repository),
        )
    }

}