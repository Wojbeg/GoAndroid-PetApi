package com.wojbeg.petapp.presentation.pet_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.wojbeg.petapp.common.Constants.DESCRIPTION_MAX_CHAR
import com.wojbeg.petapp.common.Constants.FOOD_MAX_CHAR
import com.wojbeg.petapp.common.Constants.NAME_MAX_CHAR
import com.wojbeg.petapp.common.Constants.PHOTO_MAX_CHAR
import com.wojbeg.petapp.common.Constants.SPECIES_MAX_CHAR
import com.wojbeg.petapp.common.Constants.TIME_DELAY_AFTER_PHOTO
import com.wojbeg.petapp.common.Resource
import com.wojbeg.petapp.domain.model.Pet
import com.wojbeg.petapp.domain.use_case.DetailsUseCases
import com.wojbeg.petapp.presentation.pet_detail.components.PetDetailsEvent
import com.wojbeg.petapp.presentation.pet_detail.components.PetDetailsState
import com.wojbeg.petapp.presentation.pet_detail.components.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetDetailsViewModel @Inject constructor(
    val detailsUseCases: DetailsUseCases,
): ViewModel() {

    private val _state = mutableStateOf(
        PetDetailsState()
    )
    val state: State<PetDetailsState> = _state

    //dont want image to change rapidly, after each character user enter,
    //too much internet request, we will change text after some time
    var setPhotoJob: Job? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: PetDetailsEvent) {
        when(event) {
            is PetDetailsEvent.SetPet -> {
                val pet = Gson().fromJson(event.pet, Pet::class.java)
                _state.value = state.value.copy(
                    pet = pet,
                    photoUrl = pet.photo,
                    age = pet.age.toString()
                )
            }

            is PetDetailsEvent.EnterAge -> {
                val newAgeStr = event.value.filter {
                    it.isDigit()
                }.let {
                    if (it.isNotBlank()){
                        it.toInt().toString()
                    }  else {
                        it
                    }
                }

                val isAgeError =  newAgeStr.isBlank()

                val newAge = if (newAgeStr.isNotBlank()) {
                    newAgeStr.toInt()
                } else {
                    0
                }

                _state.value = state.value.copy(
                    ageError = isAgeError,
                    pet = state.value.pet.copy(
                        age = newAge
                    ),
                    age = newAgeStr
                )
            }

            is PetDetailsEvent.EnterDesc -> {

                _state.value = state.value.copy(
                    pet = state.value.pet.copy(
                        description = event.value.take(DESCRIPTION_MAX_CHAR)
                    )
                )
            }

            is PetDetailsEvent.EnterFood -> {
                _state.value = state.value.copy(
                    pet = state.value.pet.copy(
                        food = event.value.take(FOOD_MAX_CHAR)
                    )
                )
            }

            is PetDetailsEvent.EnterName -> {
                val isNameError = event.value.isBlank()

                _state.value = state.value.copy(
                    pet = state.value.pet.copy(
                        name = event.value.take(NAME_MAX_CHAR)
                    ),
                    nameError = isNameError
                )
            }

            is PetDetailsEvent.EnterPhoto -> {
                _state.value = state.value.copy(
                    photoUrl = event.value.take(PHOTO_MAX_CHAR)
                )

                setPhotoJob?.cancel()
                setPhotoJob = viewModelScope.launch(Dispatchers.IO) {

                    delay(TIME_DELAY_AFTER_PHOTO)
                    _state.value = state.value.copy(
                        pet = state.value.pet.copy(
                            photo = event.value.take(PHOTO_MAX_CHAR)
                        )
                    )
                }
            }

            is PetDetailsEvent.EnterSpecies -> {
                _state.value = state.value.copy(
                    pet = state.value.pet.copy(
                        species = event.value.take(SPECIES_MAX_CHAR)
                    )
                )
            }

            is PetDetailsEvent.SavePet -> {
                if (_state.value.pet.name.isBlank()) {
                    _state.value = state.value.copy(
                        nameError = true
                    )
                } else {

                    (if (_state.value.pet.id == -1) {
                        detailsUseCases.createPet(_state.value.pet)
                    } else {
                        detailsUseCases.updatePet(_state.value.pet)
                    }).onEach { result ->
                        when(result) {
                            is Resource.Error -> {
                                _state.value = state.value.copy(
                                    isLoading = false
                                )
                                _eventFlow.emit(
                                    UiEvent.ShowErrorSnackbar(result.message ?: "An unexpected error occurred")
                                )
                            }
                            is Resource.Loading -> {
                                _state.value = state.value.copy(
                                    isLoading = true
                                )
                            }
                            is Resource.Success -> {
                                _state.value = state.value.copy(
                                    isLoading = false
                                )
                                _eventFlow.emit(UiEvent.SavedSuccess)
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }
}